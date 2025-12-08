SauceDemoAutomation

This repository contains automated UI tests for the [Sauce Demo](https://www.saucedemo.com/) web application, implemented using Selenium WebDriver and Java. 

Features
- Login and authentication tests
- Product listing and sorting verification
- Add to cart and remove from cart functionality
- Reset App State verification
- Cart icon and badge validation
- Product details page tests
- Cross-browser compatible tests

Tools & Frameworks
- Java 21
- Selenium WebDriver 4.x
- TestNG / JUnit
- Maven (for dependency management)
- WebDriverWait and ExpectedConditions for stable test execution

Project Structure
- `pages/` – Page Object Model classes for modular and maintainable tests
- `tests/` – Test classes with automation scripts

Usage
1. Clone the repository
2. Configure WebDriver for your preferred browser
3. Run tests via Maven or your IDE

Notes
- Designed for learning and practice of automated UI testing
- Regression tests included for critical bugs like Reset App State not resetting Add to Cart buttons

