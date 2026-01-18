<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Smart Crop Supply Management System</title>

    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            margin: 0;
            background-color: #0f172a;
            color: #e5e7eb;
            line-height: 1.6;
        }

        header {
            background: linear-gradient(135deg, #16a34a, #22c55e);
            padding: 40px 20px;
            text-align: center;
            color: #ffffff;
        }

        header h1 {
            margin: 0;
            font-size: 2.5rem;
        }

        header p {
            font-size: 1.1rem;
            margin-top: 10px;
        }

        nav {
            background-color: #020617;
            padding: 15px;
        }

        nav ul {
            list-style: none;
            display: flex;
            justify-content: center;
            gap: 20px;
            padding: 0;
            margin: 0;
        }

        nav a {
            color: #22c55e;
            text-decoration: none;
            font-weight: bold;
        }

        nav a:hover {
            text-decoration: underline;
        }

        main {
            max-width: 1100px;
            margin: auto;
            padding: 30px 20px;
        }

        section {
            background-color: #020617;
            padding: 25px;
            margin-bottom: 30px;
            border-radius: 12px;
        }

        section h2 {
            color: #22c55e;
            margin-top: 0;
        }

        ul, ol {
            margin-left: 20px;
        }

        code {
            background-color: #111827;
            padding: 5px 8px;
            border-radius: 6px;
            color: #38bdf8;
        }

        footer {
            background-color: #020617;
            text-align: center;
            padding: 20px;
            font-size: 0.9rem;
            color: #9ca3af;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            border: 1px solid #1f2933;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #111827;
            color: #22c55e;
        }
    </style>
</head>

<body>

<header>
    <h1>Smart Crop Supply Management System</h1>
    <p>Enterprise Application Development (EAD) Project</p>
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
    <h2>📌 Overview</h2>
    <p>
        The <strong>Smart Crop Supply Management System</strong> is a desktop-based application
        designed to manage crop supply workflows between officers, farmers, and buyers.
        The system simplifies farmer registration, crop management, supply preparation,
        and inventory tracking using a modern user interface.
    </p>
</section>

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

<section id="tech">
    <h2>🛠️ Technology Stack</h2>
    <table>
        <tr>
            <th>Category</th>
            <th>Technology</th>
        </tr>
        <tr>
            <td>Language</td>
            <td>Java</td>
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
            <td>MySQL</td>
        </tr>
        <tr>
            <td>Connectivity</td>
            <td>JDBC</td>
        </tr>
    </table>
</section>

<section id="installation">
    <h2>⚙️ Installation</h2>
    <ol>
        <li>Clone the GitHub repository.</li>
        <li>Install MySQL and create a database.</li>
        <li>Import the provided <code>database.sql</code> file.</li>
        <li>Configure database credentials in the DB connection file.</li>
    </ol>
</section>

<section id="usage">
    <h2>▶️ Usage</h2>
    <p><strong>Run using JAR file:</strong></p>
    <code>java -jar SmartCrop-v1.0.0.jar</code>

    <p><strong>OR</strong></p>
    <ul>
        <li>Open the project in NetBeans / IntelliJ / Eclipse</li>
        <li>Set the Main class</li>
        <li>Run the application</li>
    </ul>
</section>

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

<footer>
    <p>© 2026 Smart Crop Supply Management System | Developed for Educational Purposes</p>
</footer>

</body>
</html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Smart Crop Supply Management System</title>

    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            margin: 0;
            background-color: #0f172a;
            color: #e5e7eb;
            line-height: 1.6;
        }

        header {
            background: linear-gradient(135deg, #16a34a, #22c55e);
            padding: 40px 20px;
            text-align: center;
            color: #ffffff;
        }

        header h1 {
            margin: 0;
            font-size: 2.5rem;
        }

        header p {
            font-size: 1.1rem;
            margin-top: 10px;
        }

        nav {
            background-color: #020617;
            padding: 15px;
        }

        nav ul {
            list-style: none;
            display: flex;
            justify-content: center;
            gap: 20px;
            padding: 0;
            margin: 0;
        }

        nav a {
            color: #22c55e;
            text-decoration: none;
            font-weight: bold;
        }

        nav a:hover {
            text-decoration: underline;
        }

        main {
            max-width: 1100px;
            margin: auto;
            padding: 30px 20px;
        }

        section {
            background-color: #020617;
            padding: 25px;
            margin-bottom: 30px;
            border-radius: 12px;
        }

        section h2 {
            color: #22c55e;
            margin-top: 0;
        }

        ul, ol {
            margin-left: 20px;
        }

        code {
            background-color: #111827;
            padding: 5px 8px;
            border-radius: 6px;
            color: #38bdf8;
        }

        footer {
            background-color: #020617;
            text-align: center;
            padding: 20px;
            font-size: 0.9rem;
            color: #9ca3af;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            border: 1px solid #1f2933;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #111827;
            color: #22c55e;
        }
    </style>
</head>

<body>

<header>
    <h1>Smart Crop Supply Management System</h1>
    <p>Enterprise Application Development (EAD) Project</p>
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
    <h2>📌 Overview</h2>
    <p>
        The <strong>Smart Crop Supply Management System</strong> is a desktop-based application
        designed to manage crop supply workflows between officers, farmers, and buyers.
        The system simplifies farmer registration, crop management, supply preparation,
        and inventory tracking using a modern user interface.
    </p>
</section>

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

<section id="tech">
    <h2>🛠️ Technology Stack</h2>
    <table>
        <tr>
            <th>Category</th>
            <th>Technology</th>
        </tr>
        <tr>
            <td>Language</td>
            <td>Java</td>
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
            <td>MySQL</td>
        </tr>
        <tr>
            <td>Connectivity</td>
            <td>JDBC</td>
        </tr>
    </table>
</section>

<section id="installation">
    <h2>⚙️ Installation</h2>
    <ol>
        <li>Clone the GitHub repository.</li>
        <li>Install MySQL and create a database.</li>
        <li>Import the provided <code>database.sql</code> file.</li>
        <li>Configure database credentials in the DB connection file.</li>
    </ol>
</section>

<section id="usage">
    <h2>▶️ Usage</h2>
    <p><strong>Run using JAR file:</strong></p>
    <code>java -jar SmartCrop-v1.0.0.jar</code>

    <p><strong>OR</strong></p>
    <ul>
        <li>Open the project in NetBeans / IntelliJ / Eclipse</li>
        <li>Set the Main class</li>
        <li>Run the application</li>
    </ul>
</section>

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

<footer>
    <p>© 2026 Smart Crop Supply Management System | Developed for Educational Purposes</p>
</footer>

</body>
</html>
