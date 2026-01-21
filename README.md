<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
</head>
<body>

<header>
    <h1>🌱 Smart Crop Supply Management System</h1>
    <h3>Enterprise Application Development (EAD) Project</h3>
    <p><strong>Academic Desktop Application</strong></p>
    <hr>
</header>

<p align="center">
  <img src="https://img.shields.io/badge/License-MIT%20%2B%20Apache--2.0-blue.svg" />
  <img src="https://img.shields.io/badge/Java-JDK%2021-orange.svg" />
  <img src="https://img.shields.io/badge/Database-MySQL-blue.svg" />
  <img src="https://img.shields.io/badge/Reports-JasperReports-green.svg" />
</p>

<nav>
    <ul>
        <li><a href="#overview">Overview</a></li>
        <li><a href="#features">Key Features</a></li>
        <li><a href="#tech">Technology Stack</a></li>
        <li><a href="#architecture">System Architecture</a></li>
        <li><a href="#installation">Installation Guide</a></li>
        <li><a href="#usage">How to Run</a></li>
        <li><a href="#roles">User Roles</a></li>
        <li><a href="#reports">Reports</a></li>
        <li><a href="#future">Future Enhancements</a></li>
        <li><a href="#credentials">Default Credentials</a></li>
        <li><a href="#license">License</a></li>
    </ul>
</nav>

<hr>

<main>

<section id="overview">
    <h2>📌 Project Overview</h2>
    <p>
        The <strong>Smart Crop Supply Management System</strong> is a Java-based desktop
        application developed as part of the <strong>Enterprise Application Development (EAD)</strong> module.
        The system digitalizes crop supply operations by connecting officers, farmers, and buyers
        through a centralized management platform.
    </p>
    <p>
        It enables efficient farmer registration, crop handling, supply preparation,
        inventory management, invoice generation, and reporting using a modern UI
        and a relational database.
    </p>
</section>

<hr>

<section id="features">
    <h2>✨ Key Features</h2>
    <ul>
        <li>Role-based authentication (Admin, Officer, Buyer)</li>
        <li>Secure login and registration system</li>
        <li>Farmer and crop management</li>
        <li>Supply preparation and inventory tracking</li>
        <li>Buyer request and invoice management</li>
        <li>JasperReports integration (PDF reports)</li>
        <li>Modern dark-themed UI using FlatLaf</li>
        <li>MySQL database integration via JDBC</li>
    </ul>
</section>

<hr>

<section id="tech">
    <h2>🛠️ Technology Stack</h2>
    <table border="1" cellpadding="6">
        <tr>
            <th>Category</th>
            <th>Technology</th>
        </tr>
        <tr>
            <td>Programming Language</td>
            <td>Java (JDK 21)</td>
        </tr>
        <tr>
            <td>UI Framework</td>
            <td>Java Swing</td>
        </tr>
        <tr>
            <td>Layout Manager</td>
            <td>MigLayout</td>
        </tr>
        <tr>
            <td>Look & Feel</td>
            <td>FlatLaf (Dark)</td>
        </tr>
        <tr>
            <td>Database</td>
            <td>MySQL (phpMyAdmin)</td>
        </tr>
        <tr>
            <td>Database Connectivity</td>
            <td>JDBC</td>
        </tr>
        <tr>
            <td>Reporting Tool</td>
            <td>JasperReports</td>
        </tr>
    </table>
</section>

<hr>

<section id="architecture">
    <h2>🏗️ System Architecture</h2>
    <ul>
        <li>Presentation Layer: Java Swing UI</li>
        <li>Business Logic Layer: Java Controllers & Managers</li>
        <li>Data Access Layer: JDBC + MySQL</li>
        <li>Reporting Layer: JasperReports (PDF Export)</li>
    </ul>
</section>

<hr>

<section id="installation">
    <h2>⚙️ Installation Guide</h2>
    <ol>
        <li>Clone the GitHub repository to your local machine.</li>
        <li>Install MySQL Server and phpMyAdmin.</li>
        <li>Create a new database for the system.</li>
        <li>Import the provided <code>DATABASE.ead_cw.sql</code> create database named <code>ead_cw</code> and import file.</li>
        <li>Configure database credentials in the DB connection class.</li>
        <li>
            Project Default MySQL port is <code>3307</code>.
            <br>
            If had any issue , use this edition:-> 
            <code><a href="https://github.com/nimna-01/SmartCropSupplyManagementSystem/releases/download/v1.0.1/SmartCrop-v1.0.1.3306.jar">SmartCrop-v1.0.1_JAR+DB [3306]</a></code>
        </li>
        <li>
            Generated reports will be exported to:
            <code>C:/reports/</code>
            <br>
            (The folder is created automatically if it does not exist.)
        </li>
    </ol>
</section>

<hr>

<section id="usage">
    <h2>▶️ How to Run the Application</h2>
    <p><strong>Run using JAR file:</strong></p>
    <pre>java -jar SmartCrop-v1.0.1.jar</pre>
    <p><strong>OR run from IDE:</strong></p>
    <ul>
        <li>Open the project in IntelliJ IDEA / NetBeans / Eclipse</li>
        <li>Set the main class</li>
        <li>Run the application</li>
    </ul>
</section>

<hr>

<section id="roles">
    <h2>👥 User Roles & Responsibilities</h2>
    <table border="1" cellpadding="6">
        <tr>
            <th>Role</th>
            <th>Description</th>
        </tr>
        <tr>
            <td>Admin</td>
            <td>Manages system users, configurations, and overall monitoring</td>
        </tr>
        <tr>
            <td>Officer</td>
            <td>Handles farmers, crops, supply preparation, inventory, and reports</td>
        </tr>
        <tr>
            <td>Buyer</td>
            <td>Requests crops, views invoices, and checks supply status</td>
        </tr>
    </table>
</section>

<hr>

<section id="reports">
    <h2>📊 Reporting Module</h2>
    <ul>
        <li>Farmer Report</li>
        <li>Crop Report</li>
        <li>Supply & Inventory Report</li>
        <li>Invoice Report (PDF)</li>
    </ul>
    <p>
        Reports are generated using <strong>JasperReports</strong>
        and exported as PDF files.
    </p>
</section>

<hr>

<section id="future">
    <h2>🚀 Future Enhancements</h2>
    <ul>
        <li>Advanced analytics dashboards</li>
        <li>Email and notification system</li>
        <li>PDF export support</li>
        <li>Improved security and validation</li>
        <li>Web-based and mobile versions</li>
    </ul>
</section>

<hr>

<section id="credentials">
    <h2>🗝️ Default Login Credentials</h2>
    <ul>
        <li><strong>ADMIN</strong> → username: <code>admin</code> | password: <code>1234</code></li>
        <li><strong>OFFICER</strong> → username: <code>officer</code> | password: <code>1234</code></li>
        <li><strong>BUYER</strong> → username: <code>buyer</code> | password: <code>1234</code></li>
    </ul>
</section>

<hr>

<section id="license">
    <h2>📄 License</h2>
    <p>
        This project is licensed under the <strong>MIT License</strong>.
        <br>
        See the <code>LICENSE</code> file for details.
    </p>
</section>

</main>

<hr>

<footer>
    <p>
        © 2026 Smart Crop Supply Management System<br>
        Developed by <strong>KGL Sandanimne</strong><br>
        For Educational & Academic Purposes
    </p>
</footer>

</body>
</html>
