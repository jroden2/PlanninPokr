# PlanninPokr

A lightweight, self-contained, and Dockerized Planning Poker application built with Java Spring Boot. This tool is designed for agile teams to conduct estimation sessions in real-time with a clean, responsive UI.

## 🚀 Features

* **Real-time Interaction:** Uses WebSockets (SockJS + STOMP) for instant updates.
* **Modern UI:** Built with **Bootstrap 5** and **Font Awesome 6 (Free)**.
* **Light/Dark Mode:** Native theme toggling for user preference.
* **Docker Ready:** Optimized for containerized deployments.
* **Embedded Architecture:** Designed for easy setup and low overhead.

## 🛠️ Tech Stack

* **Backend:** Java Spring Boot
* **Frontend:** Thymeleaf, Bootstrap 5, Font Awesome 6
* **Messaging:** WebSockets (SockJS + STOMP)
* **Database:** MongoDB
* **Containerization:** Docker

---

## ⚙️ Configuration

The application is configured via environment variables. You can define these in a `.env` file in the project root:

* **APP_NAME**: The custom title displayed in the application UI.
* **MONGODB_URL**: The connection string for your MongoDB instance (e.g., mongodb://mongo:27017/db).

```
APP_NAME=PlanninPokr
MONGODB_URI=mongodb+srv://poker:example@localhost:27017/test-poker
```

---

## 📦 Getting Started

### Prerequisites
* Docker and Docker Compose installed on your machine.

### Quick Start

1. **Clone the repository:**
   `git clone https://github.com/jroden2/PlanninPokr.git`
   `cd PlanninPokr`

2. **Setup Environment:**
   Create a `.env` file with your `APP_NAME` and `MONGODB_URL`.

3. **Run the Application:**
   `docker-compose up --build -d`

4. **Access the App:**
   Open your browser to `http://localhost:8080`.

---

## 🎨 UI & UX
The application features a responsive design with a toggleable **Light/Dark mode**. Thanks to the WebSocket integration, all participants experience real-time card reveals and resets without manual page refreshes.

## 📄 License
This project is licensed under the **MIT License**.

## 🤝 Contributing
Contributions are welcome! Feel free to submit a Pull Request or open an issue for feature requests.
