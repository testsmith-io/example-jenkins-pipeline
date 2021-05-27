// Adds a pipeline job to jenkins
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import org.jenkinsci.plugins.workflow.flow.FlowDefinition
import hudson.plugins.git.GitSCM
import hudson.plugins.git.BranchSpec
import hudson.plugins.git.UserRemoteConfig
import com.cloudbees.hudson.plugins.folder.*

// Required plugins: 
// - git
// - workflow-multibranch
//

// Variables
String jobName = "training_java_unit_testing"
String jobDescription = "Pipeline job"
String jobScript = "Jenkinsfile"
String gitRepo = "https://github.com/testsmith-io/training-java-unit-testing.git"
String gitRepoName = "training-java-unit-testing"
String gitRepoBranches = "*"
String credentialsId = ""

// Create pipeline
Jenkins jenkins = Jenkins.instance

// Create the git configuration
UserRemoteConfig userRemoteConfig = new UserRemoteConfig(gitRepo, gitRepoName, null, credentialsId)
branches = Collections.singletonList(new BranchSpec(gitRepoBranches))
doGenerateSubmoduleConfigurations = false
submoduleCfg = null
browser = null
gitTool = null
extensions = []
GitSCM scm = new GitSCM([userRemoteConfig], branches, doGenerateSubmoduleConfigurations, submoduleCfg, browser, gitTool, extensions)

// Create the workflow
FlowDefinition flowDefinition = (FlowDefinition) new CpsScmFlowDefinition(scm, jobScript)

// Create job
job = jenkins.createProject(WorkflowJob, jobName)

// Add the workflow to the job
job.setDefinition(flowDefinition)
job.description = jobDescription

// Save
job.save()