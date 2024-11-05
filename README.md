
# Capstone-CS1-1 Project Documentation

## Project Introduction

**Project Number:** CS1

**Project Source:** School of Civil Engineering / Truji Pty Ltd

**Project Title:** Neighbourhood Walk - Bridging the Gap Within the Community

**Project Description and Scope:**

The goal of this project is to develop an application that connects parents and community walkers for neighborhood walks. Parents can register to find a walker who lives nearby and can walk their children (primary school-aged) to school. Walkers, who are residents of the same neighborhood, can also register to offer their walking services. Once a parent finds a suitable walker and the walker agrees, a "Neighbourhood Walk" agreement is formed between the two parties.

The system aims to build stronger community ties by making it easier for families and walkers to connect and schedule these walks via a mobile or web application.

**Expected Outcomes/Deliverables:**

- A website and/or mobile app prototype that allows users (both parents and walkers) to self-register and verify their identity.
- The system should help parents search for walkers and facilitate agreements between the two parties.
- Seamless integration with third-party identity verification systems for verifying home addresses and checks like the "Working with Children" check.
- The application should have user-friendly registration and search features for both parents and walkers.
- Potential integration with AI/ML to help match parents with walkers based on proximity and preferences.

**Specific Required Knowledge, Skills, and Technology:**

- **Mobile App Development**: Skills in developing either native or hybrid mobile applications.
- **Web Development**: Front-end and back-end web development skills to create the main functionality.
- **Identity and Access Management**: Integration with existing identity verification services (e.g., MyGov ID).
- **AI/ML** (optional): To enhance the pairing/matching mechanism between parents and walkers.

**Fields Involved:**
- Web Development
- Software Development
- Security/Networks
- Cloud Computing
- Mobile App Development

**Resources Provided by the Client:**
- Cloud services or development tools as needed.
- Students are responsible for finding relevant datasets.

---

## Project Structure Overview

The project is organized into various packages to maintain modularity and scalability:

### **Config Package (`config`)**:
- Contains application configuration files:
  - `AppConfig`: General application configuration.
  - `RedisConfig`: Configuration for Redis database.
  - `SwaggerConfig`: Configuration for API documentation with Swagger.
  - `WebConfig`: Web-related configuration.
  - `WebSocketConfig`: Configuration for WebSocket connections.

### **Controller Package (`controller`)**:
- Contains the RESTful API controllers, responsible for handling HTTP requests:
  - `AddressController`
  - `AgreementDetailController`
  - `ApplicationController`
  - `ChildrenController`
  - `FileUploadController`
  - `LocationController`
  - `LocationWebSocketHandler`
  - `MeetingController`
  - `ParentLocationWebSocketHandler`
  - `ParentsAddressController`
  - `RequestController`
  - `ShareLocationController`
  - `ShareRequestController`
  - `SmsController`
  - `UserController`
  - `UserVerificationController`
  - `WalkAgreementController`
  - `WalkerProfileController`

### **DTO Package (`dto`)**:
- Contains Data Transfer Objects used to transfer data between layers:
  - `Coordinates`
  - `LocationData`
  - `RequestDto`
  - `ShareRequestWithDescription`
  - `UserDto`
  - `WalkerProfileDTO`

### **Entity Package (`entity`)**:
- Contains Java entity classes that map to the database tables:
  - `Address`
  - `AgreementDetail`
  - `Application`
  - `Children`
  - `Location`
  - `ParentsAddress`
  - `PreMeeting`
  - `Request`
  - `ShareLocation`
  - `ShareRequest`
  - `User`
  - `UserPhoto`
  - `UserVerification`
  - `WalkAgreement`
  - `WalkerProfile`
  - `WalkerRating`

### **Mapper Package (`mapper`)**:
- Contains mapper interfaces for database interaction using MyBatis:
  - `AddressMapper`
  - `AgreementDetailMapper`
  - `ApplicationMapper`
  - `ChildrenMapper`
  - `LocationMapper`
  - `MeetingMapper`
  - `ParentsAddressMapper`
  - `RequestMapper`
  - `ShareLocationMapper`
  - `ShareRequestMapper`
  - `UserMapper`
  - `UserPhotoMapper`
  - `UserVerificationMapper`
  - `WalkAgreementMapper`
  - `WalkerProfileMapper`

### **Model Package (`model`)**:
- Contains data models:
  - `MessageDetails`

### **Service Package (`service`)**:
- Contains service classes for business logic:
  - `AddressService`
  - `EmailService`
  - `GeocodingService`
  - `MinioService`
  - `RedisService`
  - `ShareLocationService`
  - `SmsService`
  - `UserService`

---

## Project Setup and Local Deployment Guide (Maven)

To run the project locally using Maven, follow these steps:

### 1. **Prerequisites**

Ensure the following are installed on your machine:
- **Java JDK 8+**: Make sure the JDK is properly installed and configured.
- **Maven**: Ensure that Maven is installed and the `mvn` command is available in your terminal.
- **MySQL**: Install MySQL and ensure that your MySQL server is running and accessible.
- **Redis** (Optional): Install Redis if you are using Redis caching in the project.
- **MinIO** or **AWS S3**: Install and configure MinIO or ensure access to AWS S3 if the project requires object storage.

And get the application.properties file from the development team. Create resources folder in src/main and put the application.properties file in the resources folder.
Reminder: Do not push the application.properties file to the repository. Please change the api keys and passwords in the application.properties file to your own.

### 2. **Setting Up the Database**

1. **MySQL Configuration**:
  - Update the `application.properties` file to point to your local or remote MySQL database.
  - Example configuration in `application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/CS1_db?allowPublicKeyRetrieval=true
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    ```

2. **Run SQL Scripts**:
  - Ensure that the necessary database tables for `PreMeeting`, `User`, and other required entities are created by running the SQL scripts provided or by using Hibernate’s auto-schema generation.

### 3. **Maven Project Setup**

1. **Clone the Project**:
   ```bash
   git clone https://github.com/yaosiyuuu6/Capstone-CS1-1.git
   cd your-project-directory
   ```

2. **Install Dependencies**:
  - Use Maven to download all the required dependencies for the project:
   ```bash
   mvn clean install
   ```

3. **Configure Environment Variables**:
  - If using third-party services such as Google API, AWS, or MinIO, update the credentials and API keys in the `application.properties` file:
    ```properties
    google.api.key=your-google-api-key
    aws.accessKeyId=your-aws-access-key-id
    aws.secretKey=your-aws-secret-key
    minio.url=http://localhost:9000
    ```

### 4. **Running the Application**

Once the project is set up and the database is configured, you can run the application:

1. **Using Maven**:
  - Run the Spring Boot application from NeighborhoodWalk file

2. **Access the Application**:
  - By default, the application will run on `http://localhost:8088`.
  - Test the RESTful APIs using tools like Postman or directly via the browser for GET requests.

### 5. **Building for Production**

When you're ready to deploy the application:

1. **Package the Application**:
  - Package the application as a `.jar` file using Maven:
   ```bash
   mvn package
   ```

2. **Run the JAR file**:
  - After packaging, the JAR file will be available in the `target/` directory. Run it with:
   ```bash
   java -jar target/your-project-name.jar
   ```

---

## Project Team

- **Chenkai Yao**: cyao0459@uni.sydney.edu.au
- **Ke Zhang**: kzha0502@uni.sydney.edu.au
- **Jiazhou Wu**: jiwu0116@uni.sydney.edu.au
- **Yue Gao**: ygao0041@uni.sydney.edu.au
- **Zebing Zhang**: zzha0393@uni.sydney.edu.au
- **Yunze Wu**: yuwu0745@uni.sydney.edu.au

**Tutor**: Penghui Wen (penghui.wen@sydney.edu.au)

**Clients**:
1. Amanda Ng (amanda.ng@sydney.edu.au)
2. Clarence Cheah (clarence@truji.id)
