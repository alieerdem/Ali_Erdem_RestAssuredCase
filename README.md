# Pet Store API Testing Project

## Overview
This project contains automated API tests for a Pet Store application. The test suite is designed to validate various pet management operations through the API endpoints.

## Features
- Parallel test execution capability
- Retry mechanism for failed tests
- Comprehensive test coverage for pet management operations

## Test Coverage
The test suite includes verification of the following operations:
- Finding pets
- Adding new pets
- Deleting pets
- Updating pet information

## Technical Stack
- Java 21
- TestNG - Test framework
- Jackson - JSON processing
- Maven - Build tool

## Test Execution
Tests can be run in parallel with a thread count of 4. The test suite is configured using TestNG XML configuration.

### Running Tests
To run the test suite: mvn clean test

## Prerequisites
- Java 21 JDK
- Maven 3.x
- Access to Pet Store API endpoints
