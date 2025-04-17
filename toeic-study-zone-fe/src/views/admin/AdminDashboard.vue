<template>
  <div class="admin-dashboard">
    <!-- Header logo bar -->
    <nav class="admin-header">
      <div class="logo">
        <img :src="logo" alt="Logo" />
        <span class="brand-text">STUDY ZONE</span>
      </div>
      <div class="admin-name">Admin</div>
    </nav>

    <!-- Page Title -->
    <div class="page-title">
      <h1>Admin Dashboard</h1>
      <p>Dashboard</p>
    </div>

    <!-- Overview Cards -->
    <div class="cards">
      <div class="card">
        <i class="fas fa-user"></i>
        <div>
          <h2>1,250</h2>
          <p>Total Users</p>
        </div>
      </div>
      <div class="card">
        <i class="fas fa-clipboard-list"></i>
        <div>
          <h2>230</h2>
          <p>Total Tests</p>
        </div>
      </div>
      <div class="card">
        <i class="fas fa-chart-bar"></i>
        <div>
          <h2>15,800</h2>
          <p>Total Submissions</p>
        </div>
      </div>
      <div class="card">
        <i class="fas fa-check-circle"></i>
        <div>
          <h2>8.2</h2>
          <p>Avg. Test Score</p>
        </div>
      </div>
    </div>

    <!-- Charts & Tables (fake placeholder for now) -->
    <div class="chart-section">
      <div class="chart-card">
        <h3>User Statistics</h3>
        <div class="chart-placeholder">[Chart Here]</div>
      </div>
      <div class="chart-card">
        <h3>Test Performance</h3>
        <div class="chart-placeholder">[Line Chart Here]</div>
      </div>
    </div>

    <div class="info-section">
      <div class="info-card">
        <h3>Recent Submissions</h3>
        <ul>
          <li>Jenny Wilson - English Test</li>
          <li>Ronald Richards - Reading Test</li>
        </ul>
      </div>
      <div class="info-card">
        <h3>Active Admins</h3>
        <ul>
          <li>Robert Fox - 37</li>
          <li>Esther Howard - 27</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import logo from "@/assets/images/logo-study-zone.png";
</script>

<style scoped>
.admin-dashboard {
  padding: 2rem;
  background-color: #f9fafb;
  font-family: "Segoe UI", sans-serif;
}

.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  margin-bottom: 2rem;
}

.logo {
  display: flex;
  align-items: center;
}

.logo img {
  height: 48px;
  margin-right: 10px;
}

.brand-text {
  font-size: 1.6rem;
  font-weight: bold;
  color: #1877f2;
  text-transform: uppercase;
}

.admin-name {
  font-size: 1.2rem;
  font-weight: 600;
}

.page-title h1 {
  font-size: 2.2rem;
  margin-bottom: 0.25rem;
}

.page-title p {
  color: #777;
  margin-bottom: 2rem;
}

.cards {
  display: flex;
  gap: 1.5rem;
  flex-wrap: wrap;
  margin-bottom: 2rem;
}

.card {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  flex: 1 1 200px;
  display: flex;
  align-items: center;
  gap: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.card i {
  font-size: 2rem;
  color: #1877f2;
}

.card h2 {
  font-size: 1.6rem;
  margin: 0;
}

.card p {
  color: #555;
  margin: 0;
}

.chart-section,
.info-section {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
  margin-top: 2rem;
}

.chart-card,
.info-card {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.chart-placeholder {
  height: 200px;
  background-color: #e0e0e0;
  border-radius: 8px;
  margin-top: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #555;
}
</style>
