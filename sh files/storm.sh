#Initially launch 3 instances for supervisor, zookeeper and nimbus.

#Zookeeper
#---------------
#private
#172.31.13.89
#public
#34.217.136.39
#
#Nimbus
#---------------
#private
#172.31.9.241
#public
#54.187.63.208
#
#Supervisor
#---------------
#private
#172.31.14.2
#public
#35.163.199.242


#Change the hostnames of those instances to supervisor,zookeeper and nimbus respectively
sudo hostname nimbus
sudo hostname zookeeper
sudo hostname supervisor

#We add the private keys of the hostnames(Master and Slave nodes) in the below location

sudo vim /etc/hosts

<private ip> nimbus
<private ip> zookeeper
<private ip> supervisor

#Generate keys using the below command
ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa
sudo cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys

#Open authorized keys file for each of the instance and copy the respective keys to other hosts
sudo vim ~/.ssh/authorized_keys

#Save host key fingerprints by connecting to every node using ssh
ssh zookeeper
ssh nimbus
ssh supervisor

#--------------------------
#Java installation
# /bin/sh
#first set the properties
sudo apt-get -q -y install python-software-properties
sudo apt-get-repository -y ppa:webupd8team/java
sudo apt-get -q -y update

#In the query, state that you have accepted the license
echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections

#Finally install Oracle Java 7
sudo apt-get -q -y install oracle-java7-installer

#Update environmental variables
sudo bash -c "echo JAVA_HOME=/usr/lib/jvm/java-7-oracle/ >> /etc/environment"

#-------------------------
#Zookeeper installation
#First create a file
sudo vim /etc/apt/sources.list.d/cloudera.list

#Add the following in the file
deb [arch=amd64] http://archive.cloudera.com/cdh4/ubuntu/precise/amd64/cdh precise-cdh4 contrib
deb-src hrrp://archive.cloudera.com/cdh4/ubuntu/precise/amd64/cdh precise-cdh4 contrib

sudo apt-get -q -y update

sudo apt-get install zookeeper zookeeper-server

cd /usr/lib/zookeeper/bin/
sudo ./zkServer.sh start

#The above command should start the zookeeper. To check if the zookeeper is working properly
echo ruok | nc zookeeper 2181
echo stat | nc zookeeper 2181

#Packages to be installed
sudo apt-get -q -y install build-essentials
sudo apt-get -q -y install uuid-dev
sudo apt-get -q -y install git
sudo apt-get -q -y install pkg-config libtool autoconf automake
sudo apt-get -q -y install unzip

#Installing zeroMQ
wget http://download.zeromq.org/zeromq-2.1.7.tar.gz
tar -xzf zeromq-2.1.7.tar.gz
cd zeromq-2.1.7
./configure
make
sudo make install

#Installing JZMQ

export JAVA_HOME=/usr/lib/jvm/java-7-oracle

git clone "the url"

cd jzmq
cd src

touch classdict_noinst.stamp
CLASSPATH=.:./.:$CLASSPATH javac -d . org/zeromq/ZMQ.java org/zeromq/ZMQException.java org/zeromq/ZMQQueue.java org/zeromq/ZMQForwarder.java org/zeromq/ZMQStreamer.java

cd ..
./autogen.sh
./configure
make
sudo make install

#--------------------------
#Installing Storm

wget https://dl.dropbox.com/u/133901206/storm-0.8.2.zip
unzip storm-0.8.2.zip
sudo ln -s storm-0.8.2 storm

vim .bashrc
PATH=$PATH:"/home/ubuntu/storm"
source .bashrc

#now we have to set storm/config/storm.yaml file 
storm.zookeeper.servers:
	- "zookeeper"
	
nimbus.host: "nimbus"

storm.local.dir: "/home/ubuntu/stormlocal"

#In the above commands, zookeeper and nimbus are the host names

#Create Storm local directory
cd /home/ubuntu
mkdir stormlocal

#Launch nimbus
cd storm
bin/storm nimbus

#Launch UI
bin/storm ui

#Similarly install zeromq,jzmq and storm in the supervisor node also

Launch supervisor:
cd storm
bin/storm supervisor

#----------------------------------------

#on the client machine
cd /home/san/.storm
vim storm.yaml
#Copy contents from nimbus storm config to this file. Keep local directory on client

#Submit topology
cd /home/san/storm/storm/
bin/storm jar /home/san/storm/storm-starter-master/target/storm-starter-0.0.1-SNAPSHOT.jar storm.starter.ExclamationTopology exclamation-topology

#Kill topology
storm -kill exclamation-topology



