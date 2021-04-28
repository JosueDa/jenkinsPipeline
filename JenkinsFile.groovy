def checkout = { String subFolder, String repo ->
    checkout([
            $class      : "GitSCM",
            branches    : [[name: "*/master"]],
            extensions  : [[ $class: "RelativeTargetDirectory",
                             relativeTargetDir: subFolder
                           ]],
            userRemoteConfigs: [[
                                        url : repo,
                                        credentialsId: "jenkins"
                                ]]
    ])
}

def runner = { commandToExecute -> isUnix() ? sh(commandToExecute) : bat(commandToExecute) }


unitTest:{
    stage("Unit Testing"){
        node('NewNode'){
            checkout("tests","git@github.com:ccx54392/corecodeproject_api_.git" )
            runner 'cd tests && mvn test -DincludeGroup=unitTesting'
        }
    }
}
IntegrationTest:{
    stage("Integration Testing"){
        node('NewNode'){
            checkout("tests","git@github.com:JosueDa/corecodeproject_testing.git")
            runner 'cd tests && mvn test -Dgroups=integrationTest'
        }
    }
}
SystemTest:{
    stage("System or E2E Testing"){
        node('NewNode'){
            checkout("tests","git@github.com:JosueDa/corecodeproject_testing.git")
            runner 'cd tests && mvn test -Dgroups=systemTest'
        }
    }
}