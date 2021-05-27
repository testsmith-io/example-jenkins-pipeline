#!groovy

// imports
import jenkins.model.Jenkins
import hudson.model.ListView

// get Jenkins instance
Jenkins jenkins = Jenkins.getInstance()

// variables
def viewName = 'MyView'

// create the new view
jenkins.addView(new ListView(viewName))

// get the view
myView = hudson.model.Hudson.instance.getView(viewName)

// add a job by its name
myView.doAddJobToView('hello_world_pipeline')
//myView.doAddJobToView('MyJob2')
//myView.doAddJobToView('MyJob3')

// save current Jenkins state to disk
jenkins.save()