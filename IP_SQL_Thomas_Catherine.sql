DROP TABLE Transactions;
DROP TABLE PaintJob;
DROP TABLE CutJob;
DROP TABLE Jobs;
DROP TABLE AssemblyAccount;
DROP TABLE DepartmentAccount;
DROP TABLE ProcessAccount;
DROP TABLE Accounts;
DROP TABLE FitProcess;
DROP TABLE PaintProcess;
DROP TABLE CutProcess;
DROP TABLE Processes;
DROP TABLE Department;
DROP TABLE Assemblies;
DROP TABLE Customer;

CREATE TABLE Customer (
  cname VARCHAR2(40) NOT NULL,
  address VARCHAR2(40),
  PRIMARY KEY (cname));
  
CREATE UNIQUE INDEX customer_index ON Customer(cname);

CREATE TABLE Department (
  department_no NUMBER NOT NULL,
  department_data VARCHAR2(150),
  PRIMARY KEY (department_no));
  
CREATE UNIQUE INDEX department_index ON Department(department_no);

CREATE TABLE Assemblies (
  assembly_id NUMBER NOT NULL,
  assembly_details VARCHAR2(150),
  date_ordered VARCHAR2(15),
  cname VARCHAR2(40),
  PRIMARY KEY (assembly_id),
  FOREIGN KEY (cname) REFERENCES Customer(cname));
  
CREATE UNIQUE INDEX assemblies_index ON Assemblies(assembly_id);
  
CREATE TABLE Processes (
  process_id NUMBER NOT NULL,
  process_data VARCHAR2(150),
  department_no NUMBER,
  PRIMARY KEY (process_id),
  FOREIGN KEY (department_no) REFERENCES Department(department_no));
  
CREATE UNIQUE INDEX processes_index ON Processes(process_id);

CREATE TABLE FitProcess (
  process_id NUMBER NOT NULL,
  fit_type VARCHAR2(30),
  FOREIGN KEY (process_id) REFERENCES Processes(process_id)
  ON DELETE CASCADE);
  
CREATE TABLE PaintProcess (
  process_id NUMBER NOT NULL,
  paint_type VARCHAR2(30),
  painting_method VARCHAR2(30),
  FOREIGN KEY (process_id) REFERENCES Processes(process_id)
  ON DELETE CASCADE);
  
CREATE TABLE CutProcess (
  process_id NUMBER NOT NULL,
  cutting_type VARCHAR2(30),
  machine_type VARCHAR2(30),
  FOREIGN KEY (process_id) REFERENCES Processes(process_id)
  ON DELETE CASCADE);
  
CREATE TABLE Jobs (
  job_no NUMBER NOT NULL,
  start_date VARCHAR2(15),
  completed_date VARCHAR2(15),
  process_id NUMBER,
  assembly_id NUMBER,
  labor_time NUMBER,
  PRIMARY KEY (job_no),
  FOREIGN KEY (process_id) REFERENCES Processes(process_id),
  FOREIGN KEY (assembly_id) REFERENCES Assemblies(assembly_id));
  
  CREATE UNIQUE INDEX jobs_index ON Jobs(job_no);
  
  CREATE TABLE PaintJob (
    job_no NOT NULL,
    color VARCHAR2(15),
    volume_paint NUMBER,
    FOREIGN KEY (job_no) REFERENCES Jobs(job_no)
    ON DELETE CASCADE);
    
  CREATE INDEX paintjob_index ON PaintJob(color);
  
  CREATE TABLE CutJob (
    job_no NOT NULL,
    machine_type VARCHAR2(30),
    machine_time NUMBER,
    material VARCHAR2(30),
    FOREIGN KEY (job_no) REFERENCES Jobs(job_no)
    ON DELETE CASCADE);
    
  CREATE INDEX cutjob_index ON CutJob(job_no);
  
  CREATE TABLE Accounts (
    account_no NUMBER NOT NULL,
    date_established VARCHAR2(15),
    PRIMARY KEY (account_no));
    
  CREATE UNIQUE INDEX accounts_index ON Accounts(account_no); 
  
  CREATE TABLE AssemblyAccount (
    account_no NUMBER NOT NULL,
    assembly_cost NUMBER,
    assembly_id NUMBER,
    FOREIGN KEY (account_no) REFERENCES Accounts(account_no) ON DELETE CASCADE,
    FOREIGN KEY (assembly_id) REFERENCES Assemblies(assembly_id));
    
    CREATE INDEX assemblyaccounts_index ON AssemblyAccount(assembly_id); 
    
  CREATE TABLE ProcessAccount (
    account_no NUMBER NOT NULL,
    process_cost NUMBER,
    process_id NUMBER,
    FOREIGN KEY (account_no) REFERENCES Accounts(account_no) ON DELETE CASCADE,
    FOREIGN KEY (process_id) REFERENCES Processes(process_id));
    
    CREATE INDEX processaccounts_index ON ProcessAccount(account_no); 
  
  CREATE TABLE DepartmentAccount (
    account_no NUMBER NOT NULL,
    department_cost NUMBER,
    department_no NUMBER,
    FOREIGN KEY (account_no) REFERENCES Accounts(account_no) ON DELETE CASCADE,
    FOREIGN KEY (department_no) REFERENCES Department(department_no));
    
    CREATE INDEX departmentaccounts_index ON DepartmentAccount(account_no); 
    
  CREATE TABLE Transactions (
    transaction_no NUMBER NOT NULL,
    job_no NUMBER,
    costs NUMBER,
    PRIMARY KEY (transaction_no),
    FOREIGN KEY (job_no) REFERENCES Jobs(job_no));
  
  
  