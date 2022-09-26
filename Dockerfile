FROM jenkins/jenkins:2.370-jdk11

USER root
# get maven 3.3.9
RUN curl -fsSL -o /tmp/apache-maven-3.6.3.tar.gz http://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz

# install maven
RUN tar xzf /tmp/apache-maven-3.6.3.tar.gz -C /opt/
RUN ln -s /opt/apache-maven-3.6.3 /opt/maven
RUN ln -s /opt/maven/bin/mvn /usr/local/bin
RUN rm -f /tmp/apache-maven-3.6.3.tar.gz
ENV MAVEN_HOME /opt/maven

# remove download archive files
RUN apt-get clean

USER jenkins

# Jenkins configuration
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

COPY jenkins.yaml /usr/share/jenkins/ref/jenkins.yaml

# Jenkins plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Add groovy script to Jenkins hook
COPY --chown=jenkins:jenkins init.groovy.d/ /var/jenkins_home/init.groovy.d/