<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
</head>
<body>
<header>
    <p><h1>🌱 Smart Crop Supply Management System<h6>Enterprise Application Development(EAD) Project<h6></h1><br></p>
    <p> </p>
</header>


<nav>
    <ul>
        <li><a href="#overview">Overview</a></li>
        <li><a href="#features">Features</a></li>
        <li><a href="#tech">Technology</a></li>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#usage">Usage</a></li>
        <li><a href="#roles">User Roles</a></li>
        <li><a href="#future">Future Enhancements</a></li>
    </ul>
</nav>

<main>

<section id="overview">
    <br>
    <h2>📌 Overview</h2>
    <p>
        The <strong>Smart Crop Supply Management System</strong> is a desktop-based application
        designed to manage crop supply workflows between officers, farmers, and buyers.
        The system simplifies farmer registration, crop management, supply preparation,
        and inventory tracking using a modern user interface.
    </p>
</section>
<br>

<section id="features">
    <h2>✨ Key Features</h2>
    <ul>
        <li>Role-based login (Officer, Farmer, Buyer)</li>
        <li>Officer dashboard with statistics overview</li>
        <li>Farmer and crop management</li>
        <li>Prepare crop supply for buyers</li>
        <li>Inventory tracking and reports</li>
        <li>Modern dark-themed UI using FlatLaf</li>
        <li>MySQL database integration</li>
    </ul>
</section>
<br>
<section id="tech">
    <h2>🛠️ Technology Stack</h2>
    <table>
        <tr>
            <th>Category</th>
            <th>Technology</th>
        </tr>
        <tr>
            <td>Language</td>
            <td>Java(JDK21)</td>
        </tr>
        <tr>
            <td>UI Framework</td>
            <td>Java Swing</td>
        </tr>
        <tr>
            <td>Layout</td>
            <td>MigLayout</td>
        </tr>
        <tr>
            <td>Theme</td>
            <td>FlatLaf (Dark)</td>
        </tr>
        <tr>
            <td>Database</td>
            <td>MySQL(phpmyadmin)</td>
        </tr>
        <tr>
            <td>Connectivity</td>
            <td>JDBC</td>
        </tr>
    </table>
</section>
<br>
<section id="installation">
    <h2>⚙️ Installation</h2>
    <ol>
        <li>Clone the GitHub repository.</li>
        <li>Install MySQL and create a database.</li>
        <li>Import the provided <code>database.sql</code> file.</li>
        <li>Configure database credentials in the DB connection file.</li>
        <li>Change my mysql port <code>Defualt</code> --> <code>3307</code>.</li>
    </ol>
</section>
<br>
<section id="usage">
    <h2>▶️ Usage</h2>
    <p><strong>Run using JAR file:</strong></p>
    <code>java -jar SmartCrop-v1.0.1.jar</code>

<p><strong>OR</strong></p>
    <ul>
        <li>Open the project in NetBeans / IntelliJ / Eclipse</li>
        <li>Set the Main class</li>
        <li>Run the application</li>
    </ul>
</section>
<br>
<section id="roles">
    <h2>👥 User Roles</h2>
    <table>
        <tr>
            <th>Role</th>
            <th>Description</th>
        </tr>
        <tr>
            <td>Officer</td>
            <td>Manages farmers, crops, inventory, and supply workflow</td>
        </tr>
        <tr>
            <td>Farmer</td>
            <td>Registers crops and provides supply details</td>
        </tr>
        <tr>
            <td>Buyer</td>
            <td>Views and requests prepared crop supplies</td>
        </tr>
    </table>
</section>
<br>
<section id="future">
    <h2>🚀 Future Enhancements</h2>
    <ul>
        <li>Advanced reports and analytics</li>
        <li>Export data to PDF and Excel</li>
        <li>Notification system</li>
        <li>Improved security and validations</li>
        <li>Web-based version</li>
    </ul>
</section>

</main>
<br>
<footer>
    <p>© 2026 Smart Crop Supply Management System | Developed for Educational Purposes</p>
</footer>

</body>
</html>
