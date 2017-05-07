## Eclipse [Download](http://www.eclipse.org/downloads/eclipse-packages)

    Download Eclipse IDE for Java EE Developers 64 bit
    Extract contents to home directory

## Tomcat [Download](http://tomcat.apache.org/download-80.cgi)

    Download 8.5.14 / Binary Distributions / Core / zip
    Extract contents to home directory

    Optionally download 8.5.14 / Source Code Distributions / zip
    Move (NOT extract!) zip into tomcat directory

## Eclipse Configuration

    Window / Preferences / Server / Runtime Environments / Add...
    Select the type of runtime environment: Apache Tomcat v8.5
    Next
    Tomcat installation directory: /home/fred/apache-tomcat-8.5.14
    Finish
    OK

    Window / Show View / Other...
    Server / Servers
    OK

    No servers are available. Click this link to create a new server...
    Select the server type: Tomcat v8.5 Server
    Finish

## QuoteServlet

    File / New / Project...
    Web / Dynamic Web Project
    Next
    Project name: quotes
    Target runtime: Apache Tomcat v8.5
    Finish

    Open Associated Perspective?
    [x] Remember my decision
    Yes

    Right-click on Project Explorer / quotes / Java Resources / src
    New / Servlet
    Java package: servlets
    Class name: QuoteServlet
    Finish

    Right-click on QuoteServlet.java / Run As / Run on Server
    [x] Always use this server when running this project
    Finish

    Browse http://localhost:8080/quotes/QuoteServlet
