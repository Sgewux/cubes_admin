# Java CLI client 
This client was made for learning pourposes. The API that this client is  sending requests to, was also made by me: [cubeCRUD](https://github.com/Sgewux/cube-CRUD "cubeCRUD")

## Steps for running a Java project with maven
Run the following commands in your command line
1. `git clone https://github.com/Sgewux/cubes_admin.git ; cd cubes_admin`
2. `mvn package`
3. `mvn install dependency:copy-dependencies `
4. `java -cp target/cubes_admin-1.0-SNAPSHOT.jar:'target/dependency/*' com.sgewux.app.App`