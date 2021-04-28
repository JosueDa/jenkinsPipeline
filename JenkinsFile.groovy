def checkout = { String subFolder ->
    checkout([
            $class      : "GitSCM",
            branches    : [[name: "*/master"]],
            extensions  : [[ $class: "RelativeTargetDirectory",
                             relativeTargetDir: subFolder
                           ]],
            userRemoteConfigs: [[
                                        url : 'git@github.com:JosueDa/corecodeproject_testing.git',
                                        credentialsId: "jenkins"
                                ]]
    ])
}

def runner = { commandToExecute -> isUnix() ? sh(commandToExecute) : bat(commandToExecute) }

SystemTest:{
    stage("System or E2E Testing"){
        node('NewNode'){
            checkout("tests")
            runner 'mvn test -Dgroups=systemTest'
        }
    }
}