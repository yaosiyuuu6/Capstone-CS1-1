-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Drop and create Addresses Table
DROP TABLE IF EXISTS Addresses;
CREATE TABLE Addresses (
                           address_id CHAR(36) PRIMARY KEY,
                           address_line1 VARCHAR(255) NOT NULL,
                           address_line2 VARCHAR(255),
                           city VARCHAR(100) NOT NULL,
                           state VARCHAR(100) NOT NULL,
                           postcode VARCHAR(20) NOT NULL,
                           country VARCHAR(100) NOT NULL,
                           latitude DECIMAL(9,6),
                           longitude DECIMAL(9,6)
);

-- Drop and create Users Table
DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
                       user_id CHAR(36) PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       preferred_name VARCHAR(100),
                       password VARCHAR(100) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(15) NOT NULL,
                       address_id CHAR(36),
                       date_of_birth DATE NOT NULL,
                       communication_preference ENUM('Email', 'SMS', 'Both') DEFAULT 'Email',
                       user_type ENUM('Parent', 'Walker', 'Admin') NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (address_id) REFERENCES Addresses(address_id)
);

-- Drop and create WalkRequests Table
DROP TABLE IF EXISTS WalkRequests;
CREATE TABLE WalkRequests (
                              request_id CHAR(36) PRIMARY KEY,
                              parent_id CHAR(36),
                              child_name VARCHAR(100) NOT NULL,
                              pickup_address_id CHAR(36),
                              dropoff_address_id CHAR(36),
                              walk_date DATE NOT NULL,
                              walk_time TIME NOT NULL,
                              recurrence ENUM('None', 'Daily', 'Weekly') DEFAULT 'None',
                              status ENUM('Open', 'In Review', 'Accepted', 'Rejected', 'Cancelled') DEFAULT 'Open',
                              description TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (parent_id) REFERENCES Users(user_id),
                              FOREIGN KEY (pickup_address_id) REFERENCES Addresses(address_id),
                              FOREIGN KEY (dropoff_address_id) REFERENCES Addresses(address_id)
);

-- Drop and create WalkerApplications Table
DROP TABLE IF EXISTS WalkerApplications;
CREATE TABLE WalkerApplications (
                                    application_id CHAR(36) PRIMARY KEY,
                                    walker_id CHAR(36),
                                    request_id CHAR(36),
                                    application_status ENUM('Pending', 'Accepted', 'Rejected') DEFAULT 'Pending',
                                    application_date DATE NOT NULL,
                                    pre_meet_scheduled BOOLEAN DEFAULT FALSE,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    FOREIGN KEY (walker_id) REFERENCES Users(user_id),
                                    FOREIGN KEY (request_id) REFERENCES WalkRequests(request_id)
);

-- Drop and create WalkAgreements Table
DROP TABLE IF EXISTS WalkAgreements;
CREATE TABLE WalkAgreements (
                                agreement_id CHAR(36) PRIMARY KEY,
                                request_id CHAR(36),
                                walker_id CHAR(36),
                                start_time TIMESTAMP NOT NULL,
                                end_time TIMESTAMP NULL DEFAULT NULL,
                                tracking_data JSON,
                                status ENUM('In Progress', 'Completed', 'Cancelled') DEFAULT 'In Progress',
                                rating_value INTEGER CHECK (rating_value BETWEEN 1 AND 5),
                                comments TEXT,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                FOREIGN KEY (request_id) REFERENCES WalkRequests(request_id),
                                FOREIGN KEY (walker_id) REFERENCES Users(user_id)
);

-- Drop and create UserVerification Table
DROP TABLE IF EXISTS UserVerification;
CREATE TABLE UserVerification (
                                  verification_id CHAR(36) PRIMARY KEY,
                                  user_id CHAR(36),
                                  verification_type ENUM('Identity', 'Address', 'Working with Children Check') NOT NULL,
                                  verification_status ENUM('Pending', 'Verified', 'Failed') DEFAULT 'Pending',
                                  verification_date TIMESTAMP NOT NULL,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Drop and create WalkerProfile Table
DROP TABLE IF EXISTS WalkerProfile;
CREATE TABLE WalkerProfile (
                               walker_id CHAR(36) PRIMARY KEY,
                               working_with_children_check BOOLEAN DEFAULT FALSE,
                               available_dates_times SET('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'),
                               skills JSON,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (walker_id) REFERENCES Users(user_id)
);

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Insert sample data into Addresses Table
INSERT INTO Addresses (address_id, address_line1, address_line2, city, state, postcode, country, latitude, longitude)
VALUES (UUID(), '123 Main St', '', 'Sydney', 'NSW', '2000', 'Australia', -33.868820, 151.209290);

-- Insert sample data into Users Table
INSERT INTO Users (user_id, first_name, last_name, preferred_name, password, email, phone_number, address_id, date_of_birth, communication_preference, user_type)
VALUES
    (UUID(), 'John', 'Doe', 'Johnny', 'password123', 'john.doe@example.com', '0412345678', (SELECT address_id FROM Addresses LIMIT 1), '1980-05-15', 'Email', 'Parent'),
    (UUID(), 'Alice', 'Smith', 'Ally', 'password123', 'alice.smith@example.com', '0423456789', (SELECT address_id FROM Addresses LIMIT 1), '1985-03-22', 'SMS', 'Walker');

-- Insert sample data into WalkRequests Table
INSERT INTO WalkRequests (request_id, parent_id, child_name, pickup_address_id, dropoff_address_id, walk_date, walk_time, recurrence, status, description)
VALUES (UUID(), (SELECT user_id FROM Users WHERE first_name = 'John' LIMIT 1), 'Jane Doe', (SELECT address_id FROM Addresses LIMIT 1), (SELECT address_id FROM Addresses LIMIT 1), '2024-09-01', '08:00:00', 'None', 'Open', 'Morning walk to school.');

-- Insert sample data into WalkerApplications Table
INSERT INTO WalkerApplications (application_id, walker_id, request_id, application_status, application_date, pre_meet_scheduled)
VALUES (UUID(), (SELECT user_id FROM Users WHERE first_name = 'Alice' LIMIT 1), (SELECT request_id FROM WalkRequests LIMIT 1), 'Pending', '2024-08-21', FALSE);

-- Insert sample data into WalkAgreements Table
INSERT INTO WalkAgreements (agreement_id, request_id, walker_id, start_time, end_time, status, rating_value, comments)
VALUES (UUID(), (SELECT request_id FROM WalkRequests LIMIT 1), (SELECT user_id FROM Users WHERE first_name = 'Alice' LIMIT 1), CURRENT_TIMESTAMP, NULL, 'In Progress', 5, 'Great experience.');

-- Insert sample data into UserVerification Table
INSERT INTO UserVerification (verification_id, user_id, verification_type, verification_status, verification_date)
VALUES (UUID(), (SELECT user_id FROM Users WHERE first_name = 'John' LIMIT 1), 'Identity', 'Verified', CURRENT_TIMESTAMP);

-- Insert sample data into WalkerProfile Table
INSERT INTO WalkerProfile (walker_id, working_with_children_check, available_dates_times, skills)
VALUES ((SELECT user_id FROM Users WHERE first_name = 'Alice' LIMIT 1), TRUE, 'Monday,Wednesday,Friday', '{"first_aid": true, "dog_handling": true}');
