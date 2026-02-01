# Hands-On Databases

A CLI-based learning project for database concepts.

Learn and practice how databases store and query their data
going through small but focused tasks.

Each task is self-contained and focuses on a specific database concept.
Feel free to copy implementations or code between the tasks as needed.
And don't hesitate to throw away the code and start from scratch when starting a new task.

## Quick Start

### Setup

The recommended IDE is [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).
It should take care of importing the Gradle project, and downloading the expected JDK.

Also, `gradlew` will take care of the build and dependencies.

### Running the Application

Use the provided scripts to run the CLI:

```sh
# List the available tasks and arguments
./run

# Describe the options of the task with ID 1
./run -t 1

# Execute action 'read' of task 1
./run -t 1 -a read

# Execute action 'store' of task 1 with data "123"
./run -t 1 -a store -d "123"
```

## Task Progress

Track your progress as you complete each task:

| Task | Status | Description |
|------|--------|-------------|
| Task 01 | ![Task01](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/ivancea/hands-on-databases/gh-badges/task01-main.json) | Store and read a single number |
| Task 02 | ![Task02](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/ivancea/hands-on-databases/gh-badges/task02-main.json) | Store and read an array of integers |

### Badge Legend
- ⚪ **Not Started** - Task implementation not yet begun
- 🔴 **Failing** - Tests are failing (implementation incomplete or incorrect)
- 🟢 **Passing** - All tests passing!

> **Note for forked repositories:** After forking, update the badge URLs in this README to point to your repository:
> ```
> https://raw.githubusercontent.com/ivancea/hands-on-databases/gh-badges/taskXX-main.json
> ```
> Badges will automatically update after your first push triggers the workflow.

### Complete the tasks

Each task is a separate module under `tasks/taskXX/`. To complete a task:

1. Find the task class at `tasks/taskXX/src/main/java/xyz/ivancea/handsondatabases/tasks/taskXX/TaskXX.java`
2. Implement the available methods
3. Use the provided `FileHelper` to read/write files.
   The task will have full control over the given directory, and no other task will access it.
4. Run the task actions with `./run -t XX -a <action>` to test your implementation. 
   Different actions may require different data inputs.

**Example:** Task 01 is located at [tasks/task01/src/main/java/xyz/ivancea/handsondatabases/tasks/task01/Task01.java](tasks/task01/src/main/java/xyz/ivancea/handsondatabases/tasks/task01/Task01.java)

## Solutions

The solutions are available in the branch `solutions`. Feel free to take a look, but always try to solve the tasks yourself first.

Existing solutions are just a reference: there are many ways to implement each task.
The important part is to understand the concepts and complexity behind each task and solution.

## Roadmap

### Features

- Provide a module for shared tasks code
- Tests for each task (Should they be part of the task instead?)
- Performance tests for each task, checking that the complexity requirements are met

#### Technical

- GitHub workflow to keep the `solutions` branch up to date with `main`

### Tasks

- Store an integer in a file
  _(Basic, no complexity requirements)_
  - Read the integer
- Store an array of integers in a file
  _(Basic, no complexity requirements)_
  - Append more integers
  - Read the integers
- Read an integer at an index
  _(Do not iterate all the integers)_
- Search if an integer is present or not
  _(Do not iterate all the integers)_
- Multiple tables
  _(Work with multiple files)_
- Allow multiple integer columns
  _(Maintain previous requirements on the primary key)_
  - Allow marking a column as a primary key, to quickly find it and access the row data. Unique values only?
- Add columns of other types: boolean and double
  _(Metadata header or separated file)_
- Add columns of char[] type, with a fixed length
- Modify values
- Add columns of strings with variable length