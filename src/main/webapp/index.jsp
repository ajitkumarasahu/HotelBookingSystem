<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8"/>
    <title>Hotel Booking System</title>
    <link rel="stylesheet" href="css/styles.css">
  </head>
  <body>
   <header class="topbar">
        <div class="container">
          <h1 class="brand">Booking</h1>
          <nav class="topnav">
            <a href="index.jsp">Home</a>
            <a href="customers.html">Customers</a>
            <a href="rooms.html">Rooms</a>
            <a href="booking.html" class="active">Booking</a>
            <a href="payments.html">Payments</a>
            <a href="reviews.html">Review</a>
            <a href="notifications.html">Notifications</a>
          </nav>
        </div>
      </header>

    <main class="container main-card">
      <section class="hero">
        <h2>Welcome</h2>
        <p class="lead">Manage customers, rooms, bookings and payments — fully functional full-stack demo.</p>
        <div class="cta-row">
          <a class="btn" href="customers.html">Manage Customers</a>
          <a class="btn btn-secondary" href="rooms.html">Manage Rooms</a>
          <a class="btn btn-ghost" href="booking.html">Make Booking</a>
        </div>
      </section>

      <section class="features">
        <article class="card">
          <h3>Search Rooms</h3>
          <p>Find available rooms for given dates.</p>
        </article>
        <article class="card">
          <h3>Book & Pay</h3>
          <p>Create bookings and record payments quickly.</p>
        </article>
        <article class="card">
          <h3>Simple UI</h3>
          <p>Mobile friendly, clean interactions and instant feedback.</p>
        </article>
      </section>
    </main>

    <footer class="foot">
      <div class="container">
        <small>© HotelBookingSystem</small>
      </div>
    </footer>
  </body>
</html>
