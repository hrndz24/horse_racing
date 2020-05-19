Web application ***Horse racing***

*Brief description:*

The main purpose of the app is to provide users with a betting service,
where they can stake money upon horse racing events.
The user can bet on any horse of any of the future races.
When a race is added, the bookmaker should calculate the odds
and place them for all of the upcoming events.
After the race is finished it's administrator's job to set the
results (aka horse winner). Then clients' and bookmaker's balances
are updated automatically based on corresponding odds. 

*The website can be viewed in two languages: English and Spanish.*

Website visitors are provided with the following services:


***Client:***
* bet with money from their account;
* edit or delete their bets;
* replenish account;
* change their personal information;
* view upcoming races;
* view past races;
* view odds;
* view horses' statistics;
* deactivate their app account;

***Bookmaker:***
* place odds for a race;
* edit odds on a race;
* *money won/lost by clients is withdrawn from/added to bookmaker's account;*

***Administrator:***
* add race;
* edit race's information;
* delete race;
* set the winner of a race;
* add horse;
* edit horse's information;
* invalidate or validate horses;

***Guest:***
* sign up;

Libraries used for the project:
* Java 8;
* JavaEE: Servlet, JSP;
* Server / Servlet container: Tomcat 9
* Database: MySQL;
* JDBC;
* Logger: Log4J2;
* Tests: JUnit 4;