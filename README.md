🏨 Hotel Booking Management System<br>
This system allows users to manage hotel rooms, bookings, payments, notifications, and booking history through RESTful APIs and<br> a web interface.<br>

🚀 Features<br>

🔐 User Authentication (Signup / Login)<br>
🏠 Room Management (CRUD)<br>
📅 Booking Management (CRUD + availability check)<br>
💳 Payment Management<br>
🔔 Notification System (CRUD)<br>
🕒 Booking History<br>
🌐 RESTful APIs (Tested with Postman)<br>
🧩 Service Layer for business logic<br>
📦 Maven-based project<br>
🗄️ MySQL database integration<br>
🚀 Deployable on Apache Tomcat<br>

| Layer        | Technology            |<br>
| ------------ | --------------------- |<br>
| Backend      | Java, Servlets, JDBC  |<br>
| Build Tool   | Maven                 |<br>
| Database     | MySQL                 |<br>
| Server       | Apache Tomcat         |<br>
| API Testing  | Postman               |<br>
| JSON Mapping | Jackson               |<br>


📁 Project Folder Structure<br>

HotelBookingSystem/<br>
├── pom.xml<br>
├── src<br>
│   ├── main<br>
│   │   ├── java<br>
│   │   │   └── com.hotelbooking<br>
│   │   │       ├── dao<br>
│   │   │       │   ├── BookingDAO.java<br>
│   │   │       │   ├── RoomDAO.java<br>
│   │   │       │   ├── PaymentDAO.java<br>
│   │   │       │   ├── NotificationDAO.java<br>
│   │   │       │   └── UserDAO.java<br>
│   │   │       ├── model<br>
│   │   │       │   ├── Booking.java<br>
│   │   │       │   ├── Room.java<br>
│   │   │       │   ├── Payment.java<br>
│   │   │       │   ├── Notification.java<br>
│   │   │       │   └── User.<br>
│   │   │       ├── service<br>
│   │   │       │   ├── BookingService.java<br>
│   │   │       │   ├── RoomService.java<br>
│   │   │       │   ├── PaymentService.java<br>
│   │   │       │   ├── NotificationService.java<br>
│   │   │       │   └── UserService.java<br>
│   │   │       ├── servlet<br>
│   │   │       │   ├── BookingServlet.java<br>
│   │   │       │   ├── RoomServlet.java<br>
│   │   │       │   ├── PaymentServlet.java<br>
│   │   │       │   ├── NotificationServlet.java<br>
│   │   │       │   └── AuthServlet.java<br>
│   │   │       └── utils<br>
│   │   │           └── DBConnection.java<br>
│   │   ├── resources<br>
│   │   │   └── db.properties<br>
│   │   └── webapp<br>
│   │       ├── index.html<br>
│   │       ├── css/<br>
│   │       ├── js/<br>
│   │       └── WEB-<br>
│   │           └── web.xml<br>
│   └── test<br>
│       └── java<br>
└── target<br>
    └── HotelBookingSystem.war<br>

🌐 API Endpoints Overview<br>
http://localhost:8080/HotelBookingSystem/api<br>

Sample APIs<br>
Module	      Method	Endpoint<br>
Auth          POST	   /auth/signup<br>
Auth	      POST	  /auth/login<br>
Rooms	      GET	    /rooms<br>
Rooms 	      POST	   /rooms<br>
Bookings	  POST	  /bookings<br>
Bookings	  GET	     /bookings<br>
Payments	  POST	 /payments<br>
Notifications	GET	 /notifications<br>
Notifications	POST	/notifications<br>
History	        GET	    /history?customerId=1<br>

🚀 How to Run the Project<br>

1️⃣ Prerequisites<br>

JDK 8+<br>
Maven<br>
MySQL<br>
Apache Tomcat 9+<br>

2️⃣ Configure Database<br>

Create MySQL database<br>
Update db.properties<br>
db.url=jdbc:mysql://localhost:3306/hotel_db<br>
db.username=root<br>
db.password=yourpassword<br>

3️⃣ Build Project<br>
mvn clean install<br>

4️⃣ Deploy<br>

Copy HotelBookingSystem.war to:<br>
apache-tomcat/webapps/<br>
Start Tomcat<br>

5️⃣ Access Browser<br>
http://localhost:8080<br>

👨‍💻 Author<br>
Ajit Kumar Sahu<br>
email-sahuajitkumara@gmail.com<br>
Java Developer<br>
