<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">



</head>

<body>
<div class="container">

<h1>🏦 Java Console Banking System</h1>
<h3>(Core Java + JDBC + MySQL + Apache POI)</h3>

<p>
A <b>console-based banking management system</b> developed using 
<b>Core Java, JDBC, and MySQL</b>, with Excel export functionality using <b>Apache POI</b>.
</p>

<p>This project simulates real-world banking operations like account creation, deposit,
withdrawal, transfer, and passbook generation while following a <b>clean layered architecture</b>.</p>

<h2>🎨 Console Messages</h2>
<ul>
<li class="success">🟢 Green → Success</li>
<li class="error">🔴 Red → Error</li>
<li class="warning">🟡 Yellow → Warning</li>
</ul>

<hr>

<h2>🚀 Features</h2>

<h3>👤 Account Management</h3>
<ul>
<li>Create new account</li>
<li>View account details</li>
<li>Update account</li>
<li>Delete account</li>
</ul>

<h3>💰 Transaction Operations</h3>
<ul>
<li>Deposit money</li>
<li>Withdraw money</li>
<li>Transfer money between accounts</li>
<li>Check balance</li>
<li>View transaction history (passbook)</li>
</ul>

<h3>📊 Excel Export using Apache POI</h3>
<ul>
<li>Export passbook to Excel</li>
<li>Auto-generate .xlsx file</li>
<li>Structured banking report</li>
</ul>

<hr>

<h2>🛠 Technologies Used</h2>

<table>
<tr>
<th>Technology</th>
<th>Purpose</th>
</tr>
<tr>
<td>Core Java</td>
<td>Application logic</td>
</tr>
<tr>
<td>JDBC</td>
<td>Database connectivity</td>
</tr>
<tr>
<td>MySQL</td>
<td>Database</td>
</tr>
<tr>
<td>Apache POI</td>
<td>Excel export</td>
</tr>
<tr>
<td>OOP</td>
<td>Clean architecture</td>
</tr>
</table>

<hr>

<h2>🧾 Sample Console UI</h2>

<code>
1. Add Account<br>
2. Deposit Money<br>
3. Withdraw Money<br>
4. Transfer Money<br>
5. Check Balance<br>
6. Print Passbook<br>
7. Export to Excel<br>
8. Exit
</code>

<hr>

<h2>🗄 Database (MySQL)</h2>
<h3>Tables Used:</h3>
<ul>
<li>Account / Profile</li>
<li>Account Activity</li>
<li>Transaction (txn)</li>
</ul>

<h3>JDBC Operations:</h3>
<ul>
<li>Insert</li>
<li>Update</li>
<li>Delete</li>
<li>Select</li>
<li>Joins</li>
<li>Commit / Rollback</li>
</ul>

<hr>

<h2>⚙️ Setup & Run</h2>

<h3>1. Clone Repository</h3>
<code>git clone https://github.com/yourusername/your-repo-name.git</code>

<h3>2. Open in IDE</h3>
<ul>
<li>Eclipse</li>
<li>IntelliJ</li>
</ul>

<h3>3. Setup Database</h3>
<p>Update credentials in <b>DBConnection.java</b></p>

<code>
String url = "jdbc:mysql://localhost:3306/bankdb";<br>
String user = "root";<br>
String password = "root";
</code>

<h3>4. Add Apache POI Jars</h3>
<p>Add required jars in build path (Non-Maven project)</p>

<h3>5. Run Project</h3>
<code>MainApp.java</code>

<hr>

<h2>🧠 Key Concepts Implemented</h2>
<ul>
<li>JDBC connectivity</li>
<li>PreparedStatement & ResultSet</li>
<li>Transaction management</li>
<li>DAO pattern</li>
<li>Service layer architecture</li>
<li>Excel export using Apache POI</li>
<li>Colored console output</li>
</ul>

<hr>

<h2>🎯 Learning Outcome</h2>
<ul>
<li>Real-world banking logic</li>
<li>Core Java + JDBC integration</li>
<li>Clean layered structure</li>
<li>Resume-ready backend project</li>
</ul>

<hr>

<h2>📌 Future Enhancements</h2>

<h3>🔐 Role-Based Access System</h3>
<ul>
<li><b>Admin:</b> Full access, manage users, suspend/close accounts</li>
<li><b>Manager:</b> Approve large transactions, view reports</li>
<li><b>Operator:</b> Create accounts, deposit/withdraw</li>
</ul>

<h3>🚫 Account Control</h3>
<ul>
<li>Suspend account</li>
<li>Close account</li>
<li>Freeze suspicious account</li>
<li>Reactivate account</li>
</ul>

<h3>📊 Advanced</h3>
<ul>
<li>Role-based login</li>
<li>Password authentication</li>
<li>Transaction limits</li>
<li>Admin dashboard (future GUI/web)</li>
<li>Audit logs</li>
</ul>

<hr>

<h2>👨‍💻 Author</h2>
<p>
<b>Sagar</b><br>
Java Backend Developer (Fresher)<br>
Core Java • JDBC • MySQL • Spring Boot (Learning)
</p>

</div>
</body>
</html>
