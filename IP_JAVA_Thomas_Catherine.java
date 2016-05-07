import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;

public class IP_JAVA_Thomas_Catherine {
	String userName = "thom8296";
	String password = "FMzd8Ft2";
	String hostName = "oracle.cs.ou.edu";
	String serviceName = "pdborcl.cs.ou.edu";
	String portNumber = "1521";
	
	/**
	 * Get Connection
	 * Establishes a connection with an Oracle database.
	 * Uses class variables userName, password, hostname,
	 * serviceName, and portNumber to do so. 
	 * @return Connection to an oracle database. 
	 */
	public Connection getConnection(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		    Connection conn = null;
		    conn = DriverManager.getConnection("jdbc:oracle:thin:@" + hostName
		    		+ ":1521/" + serviceName,
		    		userName, password);
		    return conn;
		} catch(Exception e){
			System.out.println(e); 
			return null;
		}
	}
	
	/**
	 * Option 1
	 * Enters a new customer into the database. 
	 * @cname Name of the customer.
	 * @address Address of the customer.
	 * @conn Connection with Oracle database
	 */
	public void option1(String cname, String address, Connection conn){
		//Write SQL
		try{
			String sql = "INSERT INTO Customer (cname, address) VALUES (\'"+cname+"\',\'"+address+"\')";
			Statement stmt = conn.createStatement(); 
			stmt.executeQuery(sql);	
			System.out.println("Insert customer was a success!\n");
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 1. \n"
					+ e);
		}
	}
	
	/**
	 * Option 2
	 * Enters a new department into the database.
	 * @param departmentNumber Number of new department. Must be unique. 
	 * @param departmentData String representation of the department's data.
	 * @param conn Connection with Oracle database. 
	 */
	public void option2(int departmentNumber, String departmentData, Connection conn){
		try{
			String sql = "INSERT INTO Department (department_no, department_data) "
					+ "VALUES ("+departmentNumber+",'"+departmentData+"')";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);
			System.out.println("Insert department was a success!\n");
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 2. \n" + e);
		}
	} //end option2
	
	/**
	 * Option 3
	 * Creates a new assembly. 
	 * @param cname Name of customer associated with assembly.
	 * @param assemblyDetails Details of assembly
	 * @param assemblyid ID number of assembly
	 * @param dateOrdered Date the assembly was ordered
	 * @param conn Connection with oracle database
	 */
	public void option3(String cname, String assemblyDetails, int assemblyid, String dateOrdered, Connection conn){
		try{
			String sql = "INSERT INTO Assemblies (assembly_id, assembly_details, cname, date_ordered) "
					+ "VALUES (" + assemblyid + ",'"+assemblyDetails+"','"+cname+"','"+dateOrdered+"')";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);
			System.out.println("Insert assembly was a success!\n");
			stmt.close();
		} catch(Exception e){
			System.out.println("An error occurred on option 3. \n" + e);
		}
	} //end option3
	
	/**
	 * Option 4
	 * Creates a new process.
	 * @param processId ID number of new process
	 * @param processData Data of new process
	 * @param departmentNo Number of department that supervises this process
	 * @param type Can be cut, paint, or fit.
	 * @param methodType Can be cut type, paint type, or fit type
	 * @param optional For a paint process, this can be painting method, or for
	 * a cut process this can represent machine type.
	 * @param conn Connection with oracle database
	 */
	public void option4(int processId,String processData,int departmentNo, String type, String methodType, 
			String optional, Connection conn){
		try{
			//Insert into Processes table
			String sql = "INSERT INTO Processes (process_id, process_data,department_no) "
					+ "VALUES ("+processId+",'"+processData+"',"+departmentNo+")";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);
			
			//Insert into specific type table
			sql = "";
			if(type.equalsIgnoreCase("fit")){
				//Was a fit process, construct appropriate SQL
				sql = "INSERT INTO FitProcess (process_id, fit_type) VALUES ("+processId+",'"+methodType+"')";
			} else if (type.equalsIgnoreCase("paint")){
				//Paint process, construct appropriate SQL
				sql = "INSERT INTO PaintProcess (process_id, paint_type, painting_method) "
						+ "VALUES ("+processId+",'"+methodType+"','"+optional+"')";
			} else if (type.equalsIgnoreCase("cut")){
				//Cut process, construct appropriate SQL
				sql = "INSERT INTO CutProcess (process_id, cutting_type, machine_type) "
						+ "VALUES ("+processId+",'"+methodType+"','"+optional+"')";
			}
			
			//Execute SQL
			stmt.executeQuery(sql);
			System.out.println("Insert process was a success!\n");
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 4. \n" + e);
		}
	} //end option4
	
	/**
	 * Option 5
	 * Enters a new account and associates it with a department, process, or assembly. 
	 * @param accountNumber Unique ID nomber for the account. 
	 * @param type Can be department, process, or assembly.
	 * @param foreignId ID for the department, process, or assembly that the account 
	 * is associated with.
	 * @param dateStarted Date the account was set up.
	 * @param conn Connection to an Oracle database
	 */
	public void option5(int accountNumber,String type, int foreignId, String dateStarted, Connection conn){
		try{
			//Insert into Accounts table
			String sql = "INSERT INTO Accounts (account_no, date_established) "
					+ "VALUES ("+accountNumber+",'"+dateStarted+"')";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);
			
			//Insert into specific type table
			sql = "";
			if(type.equalsIgnoreCase("department")){
				//Was a dept account, construct appropriate SQL
				sql = "INSERT INTO DepartmentAccount (account_no, department_cost, department_no)"
						+ " VALUES ("+accountNumber+",0,"+foreignId+")";
			} else if (type.equalsIgnoreCase("assembly")){
				//Assembly account, construct appropriate SQL
				sql = "INSERT INTO AssemblyAccount (account_no, assembly_cost, assembly_id)"
						+ " VALUES ("+accountNumber+",0,"+foreignId+")";
			} else if (type.equalsIgnoreCase("process")){
				//Process account, construct appropriate SQL
				sql = "INSERT INTO ProcessAccount (account_no, process_cost, process_id)"
						+ " VALUES ("+accountNumber+",0,"+foreignId+")";
			}
			
			//Execute sql
			stmt.executeQuery(sql);
			System.out.println("Insert account was a success!\n");
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 5. \n" + e);
		}
	} //end option5
	
	/**
	 * Option 6
	 * Enters a new job into the system. 
	 * @param jobNo Unique identifier of job
	 * @param assemblyId ID of associated assembly
	 * @param processId ID of associated process
	 * @param startDate Start date of the job
	 * @param conn Connection to an Oracle database
	 */
	public void option6(int jobNo, int assemblyId, int processId, String startDate, Connection conn){
		try{
			String sql = "INSERT INTO Jobs (job_no, assembly_id, process_id, start_date)"
					+ "VALUES ("+jobNo+","+assemblyId+","+processId+",'"+startDate+"')";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);
			System.out.println("Create job was a success!");
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 6. \n" + e);
		}	
	} //end option6   
	
	/**
	 * Option 7
	 * Updates a job upon its completion. Adds information
	 * relevant to the type of job
	 * @param jobNo ID of job to be updated. 
	 * @param completedDate Date the job was completed. 
	 * @param laborTime Total labor time done on the job.
	 * @param jobType Can be fit, paint, or cut. 
	 * @param volumeOrTime Represents paint volume or machine time. 
	 * @param colorOrMType Represents paint color or machine type.
	 * @param material Material used for cut jobs.
	 * @param conn Connection to Oracle database. 
	 */
	public void option7(int jobNo, String completedDate, int laborTime, String jobType,
			int volumeOrTime, String colorOrMType, String material, Connection conn){
		try{
			String sql = "UPDATE Jobs SET completed_date='"+completedDate+"', labor_time ="+laborTime
					+" WHERE job_no="+jobNo;
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);
			
			sql = "";
			if(jobType.equalsIgnoreCase("cut")){
				sql = "INSERT INTO CutJob (job_no, machine_type, machine_time, material)"
						+ "VALUES ("+jobNo+",'"+colorOrMType+"',"+volumeOrTime+",'"+material+"')";
			} else if(jobType.equalsIgnoreCase("paint")){
				sql = "INSERT INTO PaintJob (job_no, color, volume_paint)"
						+ "VALUES ("+jobNo+",'"+colorOrMType+"',"+volumeOrTime+")";
			} 
			
			stmt.executeQuery(sql);
			System.out.println("Update job was a success!");
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 7. \n" + e);
		}
	} //end option7
	
	/**
	 * Option 8
	 * Creates a new transaction associated with a job 
	 * and updates all accounts associated with this transaction. 
	 * @param transactionNo Unique identifier for transaction
	 * @param jobNo Job this transaction will be associated with
	 * @param cost Cost of transaction
	 * @param conn Connection to Oracle database
	 */
	public void option8(int transactionNo, int jobNo, int cost, Connection conn){
		try{
			//Create transaction
			String sql = "INSERT INTO Transactions (transaction_no, job_no, costs)"
					+ " VALUES ("+transactionNo+","+jobNo+","+cost+")";
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);
			
			//Update process account
			//The updated process account is for the process used by a job.
			sql = "UPDATE ProcessAccount SET process_cost = process_cost +"+cost+" "
					+ "WHERE EXISTS (SELECT * FROM Jobs WHERE Jobs.process_id = ProcessAccount.process_id "
					+ "AND Jobs.job_no="+jobNo+")";
			stmt.executeQuery(sql);
			
			//Update department account
			//The updated department account is for the department that manages that process.
			sql = "UPDATE DepartmentAccount SET department_cost = department_cost +"+cost+" "
					+ "WHERE EXISTS (SELECT * FROM Jobs,Processes WHERE Jobs.process_id = Processes.process_id "
					+ "AND Jobs.job_no="+jobNo+" AND DepartmentAccount.department_no = Processes.department_no)";
			stmt.executeQuery(sql);
			
			//Update assembly account
			//The updated assembly account is for the assembly that requires the job.
			sql = "UPDATE AssemblyAccount SET assembly_cost = assembly_cost +"+cost+" "
					+ "WHERE EXISTS (SELECT * FROM Jobs WHERE Jobs.assembly_id = AssemblyAccount.assembly_id "
					+ "AND Jobs.job_no="+jobNo+")";
			stmt.executeQuery(sql);
			
		} catch (Exception e){
			System.out.println("An error occurred in option 8.\n" + e);
		}
	} //end option8
	
	/**
	 * Option 9
	 * Retrieves the cost for a given assembly
	 * @param assemblyId ID of assembly whose cost will be retrieved
	 * @param conn Connection to Oracle database
	 */
	public void option9(int assemblyId, Connection conn){
		try{
			String sql = "SELECT assembly_cost FROM AssemblyAccount WHERE assembly_id="+assemblyId;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("The cost for Assembly #"+assemblyId+" is: "+rs.getString("assembly_cost")); //Check
			} else {
				System.out.println("No results found.");
			}
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 9. \n" + e);
		}
	} //end option9
	
	/**
	 * Option 10
	 * Retrieves the labor time for a given assembly
	 * @param assemblyId ID of assembly whose labor time will be retrieved
	 * @param conn Connection to Oracle database
	 */
	public void option10(int assemblyId, Connection conn){
		try{
			String sql = "SELECT SUM(labor_time) sum FROM Jobs WHERE assembly_id="+assemblyId;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("The labor time for Assembly #"+assemblyId+" is: "+rs.getString("sum"));
			}
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 10. \n" + e);
		}
	}// end option10
	
	/**
	 * Option 11
	 * Retrieves total labor time in a department for jobs completed on a certain
	 * date.
	 * @param departmentNo Number of department whose total labor will be calculated.
	 * @param date Date to find total labor time.
	 * @param conn Connection to Oracle database.
	 */
	public void option11(int departmentNo, String date, Connection conn){
		try{
			String sql = "SELECT SUM(labor_time) sum FROM Jobs,Processes"
					+ " WHERE Processes.department_no="+departmentNo+" AND "
							+ "Jobs.process_id=Processes.process_id AND "
							+ "Jobs.completed_date='"+date+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("The labor time for Department #"+departmentNo+" "
						+ "on " +date+ " is: "+rs.getString("sum"));
			}
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 11. \n" + e);
		}
	} //end option11
	
	/**
	 * Option 12
	 * Retrieves processes associated with a given assembly, orders them
	 * by date, and also gives the department associated with each process. 
	 * @param assemblyId ID of assembly whose processes will be found.
	 * @param conn Connection to Oracle database.
	 */
	public void option12(int assemblyId, Connection conn){
		try{
			String sql = "SELECT Processes.process_id, Jobs.start_date, Processes.department_no FROM Jobs, Processes "
					+ "WHERE Jobs.process_id = Processes.process_id AND "
					+ "Jobs.assembly_id="+assemblyId+" ORDER BY start_date";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				System.out.println("Process id: " + rs.getString("process_id") + " Start date: " + rs.getString("start_date")
						+ " Dept. No.: " + rs.getString("department_no") );
			}
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 12. \n" + e);
		}
	} //end option12
	
	/**
	 * Retrieves all jobs, together with type information
	 * and assembly id, completed on a specified day
	 * in a certain department.
	 * @param departmentNo Number of department with associated jobs.
	 * @param date Day the jobs were completed.
	 * @param conn Connection to Oracle database.
	 */
	public void option13(int departmentNo, String date, Connection conn){
		try{

			//Get Cut Jobs
			String sql = "SELECT Jobs.job_no, Jobs.assembly_id, CutJob.machine_type,"
					+ " CutJob.machine_time, CutJob.material"
					+ "  FROM Jobs,Processes,CutJob"
					+ " WHERE Processes.department_no="+departmentNo+" AND "
					+ "Jobs.process_id=Processes.process_id AND "
					+ "Jobs.completed_date='"+date+"' AND "
					+ "CutJob.job_no = Jobs.job_no";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("Job No.: "+rs.getString("job_no")+" "
						+ " Date: " +date+ " Assembly ID: "+rs.getString("assembly_id")
						+ " Machine Type: " + rs.getString("machine_type")
						+ " Machine Time: " + rs.getString("machine_time")
						+ " Material: " + rs.getString("material"));
			}
			
			//Get Paint Jobs
			sql = "SELECT Jobs.job_no, Jobs.assembly_id, PaintJob.volume_paint,"
					+ " PaintJob.color"
					+ "  FROM Jobs,Processes,PaintJob"
					+ " WHERE Processes.department_no="+departmentNo+" AND "
					+ "Jobs.process_id=Processes.process_id AND "
					+ "Jobs.completed_date='"+date+"' AND "
					+ "PaintJob.job_no = Jobs.job_no";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("Job No.: "+rs.getString("job_no")+" "
						+ " Date: " +date+ " Assembly ID: "+rs.getString("assembly_id")
						+ " Volume Paint: " + rs.getString("volume_paint")
						+ " Color: " + rs.getString("color"));
			}
			
			//Get Fit Jobs
			sql = "SELECT Jobs.job_no, Jobs.assembly_id"
					+ "  FROM Jobs,Processes"
					+ " WHERE Processes.department_no="+departmentNo+" AND "
					+ "Jobs.process_id=Processes.process_id AND "
					+ "Jobs.completed_date='"+date+"' AND NOT EXISTS "
					+ "(SELECT * FROM CutJob,PaintJob WHERE CutJob.job_no=Jobs.job_no "
					+ "OR PaintJob.job_no = Jobs.job_no)";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("Job No.: "+rs.getString("job_no")+" "
						+ " Date: " +date+ " Assembly ID: "+rs.getString("assembly_id"));
			}
			
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 13. \n" + e);
		}
	} //end option 13
	
	/**
	 * Retrieves customers' whose assemblies are painted
	 * RED with the given paint method.
	 * @param paintMethod String stating method (ex: spray, prime, base)
	 * @param conn Connection to Oracle database
	 */
	public void option14(String paintMethod, Connection conn){
		try{
			String sql = "SELECT C.cname, C.address "
					+ "FROM Customer C, Assemblies A, Jobs J, PaintJob PJ, PaintProcess PP"
					+ " WHERE C.cname = A.cname"
					+ " AND A.assembly_id = J.assembly_id"
					+ " AND J.job_no = PJ.job_no"
					+ " AND PJ.color = 'RED'"
					+ " AND J.process_id = PP.process_id"
					+ " AND PP.painting_method='"+paintMethod+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				System.out.println("Customer name: " + rs.getString("cname") + " Address: " + rs.getString("address"));
			}
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 14.\n" + e);
		} 
	} //end option14
	
	/**
	 * Deletes all cutjobs who dates are within a given range
	 * @param jobNo1 Start number
	 * @param jobNo2 End number
	 * @param conn Connection to Oracle database
	 */
	public void option15(int jobNo1, int jobNo2, Connection conn){
		if(jobNo1 > jobNo2){
			System.out.println("Invalid input.\n");
			return;
		}
		
		try{
			String sql = "DELETE FROM CutJob"
					+ " WHERE job_no BETWEEN " + jobNo1 + " AND " + jobNo2;
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);
			System.out.println("Delete cut jobs was a success!");
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 15.\n" + e);
		}
	} //end option15
	
	/**
	 * Changes the color of a given paint job.
	 * @param job_no ID of paint job. 
	 * @param color New color. 
	 * @param conn Connection to Oracle database.
	 */
	public void option16(int job_no, String color, Connection conn){
		try{
			String sql = "UPDATE PaintJob SET color='"+color+"' WHERE job_no="+job_no;
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sql);
			System.out.println("Update color successful!");
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occurred on option 16.\n" + e);
		}
	} //end option16
	
	/**
	 * Prints the average cost of
	 * assemblies, processes, and departments.
	 * @param conn Connection to Oracle database. 
	 */
	public void option17(Connection conn){
		try{
			//Find assembly average
			String sql = "SELECT AVG(assembly_cost) assemblyCost FROM AssemblyAccount";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("Avg. Assembly Cost: " + rs.getString("assemblyCost"));
			}
			
			sql = "SELECT AVG(process_cost) processCost FROM ProcessAccount";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("Avg. Process Cost: " + rs.getString("processCost"));
			}
			
			sql = "SELECT AVG(department_cost) deptCost FROM DepartmentAccount";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("Avg. Department Cost: " + rs.getString("deptCost"));
			}
			
			stmt.close();
		} catch(Exception e){
			System.out.println("An error occurred on option 17.\n" + e);
		}
	}
	
	/**
	 * Reads in a files of customer names and addresses
	 * and inserts them into the Customer table. 
	 * @param filename Name of file with customer data
	 * @param conn Connection to Oracle database
	 * @throws FileNotFoundException Input file was not found
	 */
	public void option18(String filename, Connection conn) throws FileNotFoundException{
		Scanner in = new Scanner(new FileReader(filename));
		
		try{
			Statement stmt = conn.createStatement();
			String sql;
			
			//Read in file line by line
			while(in.hasNextLine()){
				String[] input = in.nextLine().split(",", 2);
				sql = "INSERT INTO Customer (cname, address) VALUES (\'"+input[0]+"\',\'"+input[1]+"\')";
				stmt.executeQuery(sql);
			}
			stmt.close();
			in.close();
			System.out.println("Import customers was a success!");
		} catch (Exception e){
			System.out.println("An error occurred on option 18.\n" + e);
		}
	} //end option18

	/**
	 * Prints names of customers whose assemblies are RED
	 * done with a given paint method to a file.
	 * @param filename Name of output file
	 * @param method Paint method to be search for
	 * @param conn Connection to Oracle database
	 */
	public void option19(String filename, String method, Connection conn){
		try{
			//Execute query
			String sql = "SELECT C.cname, C.address "
					+ "FROM Customer C, Assemblies A, Jobs J, PaintJob PJ, PaintProcess PP"
					+ " WHERE C.cname = A.cname"
					+ " AND A.assembly_id = J.assembly_id"
					+ " AND J.job_no = PJ.job_no"
					+ " AND PJ.color = 'RED'"
					+ " AND J.process_id = PP.process_id"
					+ " AND PP.painting_method='"+method+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			
			//Create file to write to
			File file = new File(filename);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Write to file
			while(rs.next()){
				bw.write("Customer name: " + rs.getString("cname") + " Address: " + rs.getString("address"));
			}
			stmt.close();
			bw.close();
			System.out.println("Write to file was a success!");
		} catch (Exception e){
			System.out.println("An error occurred on option 14.\n" + e);
		} 
	} //end option19
	
	/**
	 * Prints all query options. 
	 */
	public void printOptions(){
		System.out.println(
				"(1) Enter a new customer\n" +
				"(2) Enter a new department\n" +
				"(3) Enter a new assembly with its customer-name, assembly-details, assembly-id, and date ordered\n" +
				"(4) Enter a new process-id and its department together with its type and information relevant tothe type\n" +
				"(5) Create a new account and associate it with the process, assembly, or department to "
					+ "which it is applicable\n" +
				"(6) Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced\n" +
				"(7) At the completion of a job, enter the date it completed and the information relevant to the" +
					" type of job\n" +
				"(8) Enter a transaction-no, and its sup-cost and update all the costs (details) of the affected accounts\n" +
				"(9) Retrieve the cost incurred on an assembly-id \n" +
				"(10) Retrieve the labor time recorded on an assembly-id \n" +
				"(11) Retrieve the total labor time within a department for jobs completed in the "
					+ "department during a given date\n" +
				"(12) Retrieve the processes through which a given assembly-id has passed so far (in "
					+ "date commenced order) and the department responsible for each process \n" +
				"(13) Retrieve the jobs (together with their type information and assembly-id) "
					+ "completed during a given date in a given department \n" +
				"(14) Retrieve the customers (in name order) whose assemblies are painted"
					+ " RED using a given painting method \n" +
				"(15) Delete all cut-jobs whose job-no is in some range \n" +
				"(16) Change the color of a given paint job \n" +
				"(17) Retrieve the average cost of all accounts \n" +
				"(18) Import: enter new customers from a data file until the file is empty \n" +
				"(19) Export: Retrieve the customers (in name order) whose assemblies are painted RED"
					+ " using a given painting method and output them to a data file \n" +
				"(20) Quit\n" +
				"Input option #: ");
	} //end printOptions
	
	/**
	 * Prints the Customer table.
	 * @param conn Connection to Oracle database.
	 */
	public void printCustomers(Connection conn){
		try{
			String sql = "SELECT * FROM Customer";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			System.out.println("CUSTOMER TABLE\n"
					+ "cname, address\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("cname") + ", " + rs.getString("address"));
			}
			System.out.println("-------------------------------\n");
			stmt.close();
		} catch(Exception e){
			System.out.println("An error occured printing  customers.\n" + e);
		}
	} //end printCustomers
	
	/**
	 * Prints the Department table.
	 * @param conn Connection to Oracle database.
	 */
	public void printDepartments(Connection conn){
		try{
			String sql = "SELECT * FROM Department";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			System.out.println("DEPARTMENT TABLE\n"
					+ "department_no, department_data\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("department_no") + ", " + rs.getString("department_data"));
			}
			System.out.println("-------------------------------\n");
			stmt.close();
		} catch(Exception e){
			System.out.println("An error occured printing departments.\n" + e);
			return;
		}
	} //end printDepartment
	
	/**
	 * Prints the Assemblies table.
	 * @param conn Connection to Oracle database.
	 */
	public void printAssemblies(Connection conn){
		try{
			String sql = "SELECT * FROM Assemblies";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			System.out.println("ASSEMBLIES TABLE\n"
					+ "assembly_id, assembly_details, date_ordered, cname\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("assembly_id") + ", " + rs.getString("assembly_details")
						+ ", " + rs.getString("date_ordered") + ", " + rs.getString("cname"));
			}
			System.out.println("-------------------------------\n");
			stmt.close();
		} catch(Exception e){
			System.out.println("An error occured printing departments.\n" + e);
			return;
		}
	} //end printDepartment
	
	/**
	 * Prints all the Processes table.
	 * @param conn Connection to Oracle database.
	 */
	public void printProcesses(Connection conn){
		try{
			//Get processes
			String sql = "SELECT * FROM Processes";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			System.out.println("PROCESSES TABLE\n"
					+ "process_id, process_data, department_no\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("process_id") + ", " + rs.getString("process_data")
						+ ", " + rs.getString("department_no"));
			}
			System.out.println("-------------------------------\n");
			
			//Get Fit Processes
			sql = "SELECT * FROM FitProcess";
			rs = stmt.executeQuery(sql);
			
			System.out.println("FIT PROCESS TABLE\n"
					+ "process_id, fit_type\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("process_id") + ", " + rs.getString("fit_type"));
			}
			System.out.println("-------------------------------\n");
			
			//Get Paint Processes
			sql = "SELECT * FROM PaintProcess";
			rs = stmt.executeQuery(sql);
			
			System.out.println("PAINT PROCESS TABLE\n"
					+ "process_id, paint_type, painting_method\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("process_id") + ", " + rs.getString("paint_type")
						+ ", " + rs.getString("painting_method"));
			}
			System.out.println("-------------------------------\n");
			
			//Get Paint Processes
			sql = "SELECT * FROM CutProcess";
			rs = stmt.executeQuery(sql);
			
			System.out.println("CUT PROCESS TABLE\n"
					+ "process_id, cutting_type, machine_type\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("process_id") + ", " + rs.getString("cutting_type")
						+ ", " + rs.getString("machine_type"));
			}
			System.out.println("-------------------------------\n");
			
			stmt.close();
		} catch(Exception e){
			System.out.println("An error occured printing processes.\n" + e);
			return;
		}
	} //end printProcesses
	
	/**
	 * Prints all the Account tables.
	 * @param conn Connection to Oracle database.
	 */
	public void printAccounts(Connection conn){
		try{
			//Get accounts
			String sql = "SELECT * FROM Accounts";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			System.out.println("ACCOUNTS TABLE\n"
					+ "account_no, date_established\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("account_no") + ", " + rs.getString("date_established"));
			}
			System.out.println("-------------------------------\n");
			
			//Get Assembly accounts
			sql = "SELECT * FROM AssemblyAccount";
			rs = stmt.executeQuery(sql);
			
			System.out.println("ASSEMBLY ACCOUNT TABLE\n"
					+ "account_no, assembly_id, assembly_cost\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("account_no") + ", " + rs.getString("assembly_id")
						+ ", " + rs.getString("assembly_cost"));
			}
			System.out.println("-------------------------------\n");
			
			//Get process accounts
			sql = "SELECT * FROM ProcessAccount";
			rs = stmt.executeQuery(sql);
			
			System.out.println("PROCESS ACCOUNT TABLE\n"
					+ "account_no, process_id, process_cost\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("account_no") + ", " + rs.getString("process_id")
						+ ", " + rs.getString("process_cost"));
			}
			System.out.println("-------------------------------\n");
			
			//Get department accounts
			sql = "SELECT * FROM DepartmentAccount";
			rs = stmt.executeQuery(sql);
			
			System.out.println("DEPARTMENT ACCOUNT TABLE\n"
					+ "account_no, department_no, department_cost\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("account_no") + ", " + rs.getString("department_no")
						+ ", " + rs.getString("department_cost"));
			}
			System.out.println("-------------------------------\n");
			
			stmt.close();
		} catch(Exception e){
			System.out.println("An error occured printing accounts.\n" + e);
			return;
		}
	} //end printAccounts
	
	/**
	 * Prints all the Job tables.
	 * @param conn Connection to Oracle database.
	 */
	public void printJobs(Connection conn){
		try{
			//Get jobs
			String sql = "SELECT * FROM Jobs";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			System.out.println("JOBS TABLE\n"
					+ "job_no, start_date, completed_date, process_id, assembly_id, labor_time\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("job_no") + ", " + rs.getString("start_date")
						+ ", " + rs.getString("completed_date")+ ", " + rs.getString("process_id")
						+ ", " + rs.getString("assembly_id") + ", " + rs.getString("labor_time"));
			}
			System.out.println("-------------------------------\n");
			
			//Get Paint Jobs
			sql = "SELECT * FROM PaintJob";
			rs = stmt.executeQuery(sql);
			
			System.out.println("PAINT JOB TABLE\n"
					+ "job_no, color, volume\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("job_no") + ", " + rs.getString("color")
						+ ", " + rs.getString("volume_paint"));
			}
			System.out.println("-------------------------------\n");
			
			//Get Cut Jobs
			sql = "SELECT * FROM CutJob";
			rs = stmt.executeQuery(sql);
			
			System.out.println("CUT JOB TABLE\n"
					+ "job_no, machine_type, machine_time, material\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("job_no") + ", " + rs.getString("machine_type")
						+ ", " + rs.getString("machine_time")+ ", " + rs.getString("material"));
			}
			System.out.println("-------------------------------\n");
			
			stmt.close();
		} catch(Exception e){
			System.out.println("An error occured printing jobs.\n" + e);
			return;
		}
	} //end printJobs
	
	public void printTransaction(Connection conn){
		try{
			String sql = "SELECT * FROM Transactions";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("TRANSACTIONS TABLE\n"
					+ "transaction_no, job_no, cost\n"
					+ "-------------------------------");
			while(rs.next()){
				System.out.println(rs.getString("transaction_no") + ", " + rs.getString("job_no")
						+ ", " + rs.getString("costs"));
			}
			System.out.println("-------------------------------\n");
			stmt.close();
		} catch (Exception e){
			System.out.println("An error occured printing transactions\n" + e);
			return;
		}
	} //end printTransactions
	
	/**
	 * Main method. Runs a loop providing query options and receiving
	 * user input. 
	 * @param args None. 
	 * @throws IOException For invalid user input. 
	 */
	public static void main(String[] args) throws IOException{
		IP_JAVA_Thomas_Catherine app = new IP_JAVA_Thomas_Catherine();
		Connection conn = app.getConnection(); //Establish database connection
		
		//buffer to read in user input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//Start app
		System.out.println("WELCOME TO THE JOB SHOP ACCOUNTING DATABASE SYSTEM.");
		String option = "0";
		while(!option.equals("20")){
			app.printOptions();
			option = br.readLine(); // get option choice
			
			if (option.equals("1")){
				//Get user input
				System.out.println("Input customer name:");
				String cname = br.readLine();
				System.out.println("Input customer address:");
				String address = br.readLine();
				
				//Create customer
				app.option1(cname, address, conn);
				
				//Print customer table
				app.printCustomers(conn);
			} else if (option.equals("2")){
				//Get user input
				System.out.println("Input department number:");
				int number = Integer.parseInt(br.readLine());
				System.out.println("Input department data:");
				String data = br.readLine();
				
				//Create department
				app.option2(number, data, conn);
				
				//Print department table
				app.printDepartments(conn);
			} else if (option.equals("3")){
				//Get user input
				System.out.println("Input assembly id (number):");
				int id = Integer.parseInt(br.readLine());
				System.out.println("Input assembly details:");
				String details = br.readLine();
				System.out.println("Input date ordered mm-dd-yyyy:");
				String date = br.readLine();
				System.out.println("Input customer name:");
				String cname = br.readLine();
				
				//Create assembly
				app.option3(cname, details, id, date, conn);
				
				//Print table
				app.printAssemblies(conn);
			} else if(option.equals("4")){
				//Get user input
				System.out.println("Input process id (number):");
				int id = Integer.parseInt(br.readLine());
				System.out.println("Input process data:");
				String details = br.readLine();
				System.out.println("Input department number:");
				int deptNo = Integer.parseInt(br.readLine());
				System.out.println("Input the type of process (cut, paint, fit):");
				String type = br.readLine();
				System.out.println("Depending on the type of process, input the fit type, paint type, or cut type:");
				String methodtype = br.readLine();
				System.out.println("If cut process, input machine type. If paint process, input painting method:");
				String optional = br.readLine();
				
				//Create process
				app.option4(id, details, deptNo, type, methodtype, optional, conn);
				
				//Print processes
				app.printProcesses(conn);
			} else if(option.equals("5")){
				//Get user input
				System.out.println("Input account id (number):");
				int id = Integer.parseInt(br.readLine());
				System.out.println("Input date established mm-dd-yyyy:");
				String date = br.readLine();
				System.out.println("Input account type (department, assembly, process):");
				String type = br.readLine();
				System.out.println("Input ID/no. of corresponding department, assembly, or process:");
				int foreign = Integer.parseInt(br.readLine());
				
				//Create account
				app.option5(id, type, foreign, date,conn);
				
				//Print accounts
				app.printAccounts(conn);
			} else if(option.equals("6")){
				//Get user input
				System.out.println("Input job number:");
				int id = Integer.parseInt(br.readLine());
				System.out.println("Input date started mm-dd-yyyy:");
				String date = br.readLine();
				System.out.println("Input associated assembly id:");
				int assemblyId = Integer.parseInt(br.readLine());
				System.out.println("Input associated process id:");
				int processId = Integer.parseInt(br.readLine());
				
				//Create job
				app.option6(id, assemblyId, processId, date, conn);
				
				//Print job table
				app.printJobs(conn);
			} else if(option.equals("7")){
				//Get user input
				System.out.println("Input job number:");
				int id = Integer.parseInt(br.readLine());
				System.out.println("Input date completed mm-dd-yyyy:");
				String date = br.readLine();
				System.out.println("Input labor time:");
				int laborTime = Integer.parseInt(br.readLine());
				System.out.println("Input job type (paint, cut, fit):");
				String type = br.readLine();
				System.out.println("Input color (paint job) or machine type (cut job) or 'none':");
				String colorOrMType = br.readLine();
				System.out.println("Input volume (paint job) or machine time (cut job) or 0:");
				int volOrTime = Integer.parseInt(br.readLine());
				System.out.println("Input material (cut job) or 'none':");
				String material = br.readLine();
				
				//Update job
				app.option7(id, date, laborTime, type, volOrTime, colorOrMType, material, conn);
				
				//Print job table
				app.printJobs(conn);
			} else if(option.equals("8")){
				System.out.println("Input transaction number:");
				int no = Integer.parseInt(br.readLine());
				System.out.println("Input job number:");
				int job = Integer.parseInt(br.readLine());
				System.out.println("Input cost:");
				int cost = Integer.parseInt(br.readLine());
				
				//Create transaction
				app.option8(no, job, cost, conn);
				
				//Print transactions and accounts
				app.printTransaction(conn);
				app.printAccounts(conn);
			} else if(option.equals("9")){
				//Get user input
				System.out.println("Input assembly id:");
				int id = Integer.parseInt(br.readLine());
				
				//Retrieve cost
				app.option9(id, conn);
			} else if(option.equals("10")){
				//Get user input
				System.out.println("Input assembly id:");
				int id = Integer.parseInt(br.readLine());
				
				//Retrieve labor time
				app.option10(id, conn);
			} else if(option.equals("11")){
				//Get user input
				System.out.println("Input department no:");
				int id = Integer.parseInt(br.readLine());
				System.out.println("Input completion date mm-dd-yyyy:");
				String date = br.readLine();
				
				//Retrieve labor time
				app.option11(id, date, conn);
			} else if(option.equals("12")){
				//Get user input
				System.out.println("Input assembly id:");
				int id = Integer.parseInt(br.readLine());
				
				//Retrieve data
				app.option12(id, conn);
			} else if(option.equals("13")){ 
				//Get user input
				System.out.println("Input department no:");
				int id = Integer.parseInt(br.readLine());
				System.out.println("Input completion date mm-dd-yyyy:");
				String date = br.readLine();
				
				//Retrieve data
				app.option13(id, date, conn);
			} else if(option.equals("14")){
				//Get user input
				System.out.println("Paint method (ex. spray, brush, sponged):");
				String method = br.readLine();
				
				//Get customers
				app.option14(method, conn);
			} else if(option.equals("15")){
				//Get user input
				System.out.println("Start job no.:");
				int start = Integer.parseInt(br.readLine());
				System.out.println("End job no.:");
				int end = Integer.parseInt(br.readLine());
				
				//Retrieve data
				app.option15(start, end, conn);
				
				//Print Jobs
				app.printJobs(conn);
			} else if(option.equals("16")){
				//Get user input
				System.out.println("Enter paint job no.:");
				int number = Integer.parseInt(br.readLine());
				System.out.println("Enter new color:");
				String color = br.readLine();
				
				app.option16(number, color, conn);
				
				//Print Jobs
				app.printJobs(conn);
			} else if(option.equals("17")){
				app.option17(conn);
			} else if(option.equals("18")){
				//Get user input
				System.out.println("Enter name of Customer data file (should be csv with cname, address):");
				String filename = br.readLine();
				
				//Read in file
				app.option18(filename, conn);
				
				//Print customers
				app.printCustomers(conn);
			} else if(option.equals("19")){
				//Get user input
				System.out.println("Enter name of output file:");
				String filename = br.readLine();
				System.out.println("Enter name of paint method:");
				String method = br.readLine();
				
				//Read in file
				app.option19(filename, method, conn);
			} else if (option.equals("20")){
				System.out.println("Program exiting.");
			} else {
				System.out.println("Invalid option entered.\n");
			}
		} //end main loop
	} //end main method
} //end project file 
