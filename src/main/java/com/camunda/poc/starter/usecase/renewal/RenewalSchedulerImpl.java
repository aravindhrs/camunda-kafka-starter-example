package com.camunda.poc.starter.usecase.renewal;

import com.camunda.poc.starter.usecase.renewal.entity.Lease;
import com.camunda.poc.starter.usecase.renewal.repo.LeaseRepository;
import com.camunda.poc.starter.usecase.renewal.repo.TenantRepository;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Profile("renewal")
@Component
@EnableConfigurationProperties(AppConfigProperties.class)
public class RenewalSchedulerImpl {

    public static Logger log = Logger.getLogger(Class.class.getName());

    @Autowired
    AppConfigProperties config;

    @Bean
    @Profile("schedule-renewal-start")
    public RenewalScheduler leaseRenewalScheduler(final LeaseRepository leaseRepository,
                                                  final RuntimeService runtimeService,
                                                  final TaskService taskService){

        return new RenewalScheduler() {

            @Override
            @Scheduled(cron = "${app.cron.renewal-start}")
            public void run() {
                log.fine("[X] Running Lease renewal");
                //kicks off worklfow when the end date is 100 from current date
                Date leaseRenewalkickoffDate = RenewalUtil.getKickOffDate(config.getCron().getRenewalKickoffBufferDays());
                log.fine("[X] Start date from today: "+leaseRenewalkickoffDate);

                List<Lease> leases = leaseRepository.findByEndDate(leaseRenewalkickoffDate);
                if (!leases.isEmpty()){
                    for(Lease lease : leases){
                        try {
                            RenewalUtil.startLeaseRenewal(lease, leaseRepository, runtimeService, taskService, config);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }else{
                    log.fine("[X] No leases found ending with kick off date: "+leaseRenewalkickoffDate);
                }
            }
        };
    }

    //TODO: refactor this is not scalable when there are many lease
    @Bean
    public RenewalScheduler updateLeaseState(final TaskService taskService,
                                             final LeaseRepository leaseRepository) {

        return new RenewalScheduler() {

            @Override
            @Scheduled(cron = "0/10 * * * * ?")
            public void run() {
                List<Lease> leases = leaseRepository.findStarted();
                for (Lease lease : leases) {
                    List<Task> tasks = RenewalUtil.queryTasksById(taskService, lease.getProcessId());
                    if (tasks != null && !tasks.isEmpty()){
                        Task task = tasks.get(0);
                        log.info("Found Task: "+task.getName());
                        if (!task.getName().equalsIgnoreCase(lease.getWorkflowState())) {
                            lease.setWorkflowState(task.getName());
                            leaseRepository.save(lease);
                            log.info("Updating Lease State with Task: "+task.getName());
                        }
                    }
                }
            }
        };
    }

    @Bean
    @Profile("schedule-renewal-clean")
    public RenewalScheduler scheduleCleanup(final RenewalCleanupService renewalCleanupService){

        return new RenewalScheduler() {

            @Override
            @Scheduled(cron = "${app.cron.renewal-clean}")
            public void run() {
                log.fine("[X] Start Lease Cleanup: "+ Calendar.getInstance().getTime());
                renewalCleanupService.clean();
            }
        };
    }

    @Bean
    @Profile("init-roles")
    InitializingBean usersAndGroupsInitializer(final IdentityService identityService,
                                               final TenantRepository tenantRepository,
                                               final LeaseRepository leaseRepository,
                                               final RuntimeService runtimeService) {

        return new InitializingBean() {
            public void afterPropertiesSet() throws Exception {

                List<AppConfigProperties.Security> securities = config.getSecurity();
                for(AppConfigProperties.Security security : securities){
                    List<String> roles = security.getRoles();
                    for(String role : roles){
                        Group group = identityService.newGroup(role);
                        group.setName(role.toLowerCase()+"s");
                        group.setType("security-role");
                        identityService.saveGroup(group);
                    }
                    User admin = identityService.newUser(security.getUsername());
                    admin.setPassword(security.getPassword());
                    identityService.saveUser(admin);
                }
            }
        };
    }
}