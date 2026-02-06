# 🍷 WineFlow AI: Smart Client-Server Platform

An intelligent client-server web application designed to simplify wine selection using **Artificial Intelligence**. The system provides personalized recommendations by leveraging either a **Large Language Model (LLM)** or a specialized **Arbiter Algorithm**.

---

## 🚀 Key Features

* **Hybrid AI Logic:** Smart selection powered by LLMs and custom arbiter algorithms.
* **Seamless Integration:** Real-time data fetching from third-party wine databases.
* **Quick Results:** Get a personalized recommendation in **2–5 seconds**.
* **Full Cycle:** From registration and preference setting to direct purchase links.

---

## 💻 Graphical User Interface (GUI)

The following flow represents the core user experience within the application:

### 1. Access & Security
* **Authentication (Figure 4.1):** A secure login gateway with fields for credentials and a direct link to the signup page.
* **Registration (Figure 4.2):** A streamlined onboarding process requiring name, email, and password confirmation.

### 2. Exploration & Shopping
* **Main Dashboard (Figure 4.3):** The central hub where users can choose between browsing the catalog, generating cocktail recipes, or starting the AI preference form.
* **Curated Shop (Figure 4.4):** A digital catalog built from open-source drink databases, providing external links to complete purchases.

### 3. AI Recommendation Engine
* **Wine Preference Form (Figure 4.5):** A comprehensive tool where users input specific criteria (characteristics, restrictions, and additional text preferences).
* **Personalized Results (Figure 4.6):** The final output displaying the recommended drink, a purchase link, and an option to restart the search.

---

## 🛠 Technical Stack & Requirements

### Backend & Infrastructure
* **Framework:** Spring (Java)
* **Web Server:** Apache Tomcat
* **Database:** MongoDB (NoSQL)
* **Deployment:** Currently runs on a local PC with internet access (scalable to dedicated servers).

### Requirements
1.  **Internet Connection:** Required for both the application and database servers to communicate and access AI services.
2.  **Environment:** * Running **Tomcat** server (via Spring).
    * Active **MongoDB** instance.
    * Any modern **Web Browser** for the client-side.

---

## ⚙️ How to Use

1.  **Start Services:** Ensure your MongoDB and Tomcat servers are active.
2.  **Authentication:** Register a new profile or log in with existing credentials.
3.  **Submit Preferences:** Navigate to the AI Form and fill in your drink criteria.
4.  **Get Recommendation:** Wait 2–5 seconds for the AI to process your request.
5.  **Purchase:** Follow the generated link to a third-party website to buy your drink or try a new search.

---

*Developed as a project implementing modern Client-Server architecture and AI integration.*
In the software, pages with the following graphical user interface were implemented, namely:
<img width="794" alt="Screenshot 2025-07-01 at 11 57 39" src="https://github.com/user-attachments/assets/52329a65-7b0e-4cd0-a711-d1cc935e7408" />

Figure 4.1 – Example of the authentication page
Figure 4.1 presents the login page, which contains two input fields. The user can log in by clicking the "Log in" button, or click the "Registration" button, which redirects the user to the signup page.
<img width="783" alt="Screenshot 2025-07-01 at 11 57 45" src="https://github.com/user-attachments/assets/b4d263b2-3acd-4447-ad1f-7538ead9cd8f" />

Figure 4.2 – Example of the registration page
In Figure 4.2, there are four fields to fill in: the user's name or nickname, email, password, and password confirmation. After completing all fields, the user can create a profile and access the main page.
<img width="740" alt="Screenshot 2025-07-01 at 11 57 54" src="https://github.com/user-attachments/assets/d6f463cf-97fa-42d5-967f-9b8aa0ead19c" />

Figure 4.3 – Example of the main page
From the main page shown in Figure 4.3, users can navigate to a page with a selection of alcoholic beverages sourced from a third-party website, and then proceed to purchase them. Additionally, users can fill out a form to receive a cocktail recipe or submit preferences and receive an AI-generated drink suggestion, with an option to order it.
<img width="800" alt="Screenshot 2025-07-01 at 11 58 04" src="https://github.com/user-attachments/assets/15ed5037-aead-48bd-96f9-f27d59c0adc3" />

Figure 4.4 – Example of the shop page in app
The shop page shown in Figure 4.4 allows users to find drinks from a curated database, compiled using free drink databases. Users can also follow a provided link to order a selected beverage.
<img width="757" alt="Screenshot 2025-07-01 at 11 58 12" src="https://github.com/user-attachments/assets/6045f229-821c-44fb-a47f-ad3c5d751d85" />

Figure 4.5 – Example of the drink selection form
Figure 4.5 presents the WinePreferenceForm, which enables the user to receive a drink recommendation from the AI. The form contains several fields for describing alcohol characteristics using various criteria, along with a text field for additional preferences, restrictions, etc.
<img width="760" alt="Screenshot 2025-07-01 at 11 58 18" src="https://github.com/user-attachments/assets/a065f260-1a26-4859-aedc-ff31718f5cab" />

Figure 4.6 – Example of a recommended drink
Users are then able to follow a generated link to a third-party website to purchase the recommended drink. Alternatively, by clicking another button, the user can restart the process and attempt to find a different recommendation.

For the software to function properly, a running Tomcat server launched by the Spring framework is required. This can be done on a personal computer or, for larger projects, on a dedicated server. Currently, the server runs on a local PC connected to the internet.

Additionally, a running MongoDB server is required, where the database is stored. Both the application server and the database must have internet access to enable communication with each other and with the user.

To use the software, the user only needs internet access and a web browser. The user must register or log in to access the site. The main functionality is to navigate to the form, fill it out, wait between 2 to 5 seconds, and receive an AI-generated drink recommendation. The user can then follow a provided link to order the drink or try to find another one.
