Is My Internet Broken?
======================

![Build Status](https://github.com/raphaelbauer/ismyinternetbroken/workflows/Java%20CI%20with%20Maven/badge.svg)

Why?
----
I am often working with my laptop. But sometimes my internet does not work. That's bad.
But in reality it's not my internet, but the wifi connection that is flaky.

The fix is to repair the wifi connection in the first place.

A little system tray icon tells you exactly what is broken:
- Your wifi (by pinging 192.168.1.1)
- Or your ISP (by pinging  192.168.1.1 AND www.google.com)

A little system tray icon keeps you updated.


What does the icon mean?
------------------------

- ![Internet works icon](/src/main/resources/all_works.png) Congrats. All is fine.
- ![Only router works icon](/src/main/resources/only_router_works.png) Likely your ISP is broken. google.com can NOT be reached - but 192.168.1.1 can be reached.
- ![No internet icon](/src/main/resources/no_internet.png) Likely your wifi is broken. Neither 192.168.1.1 nor www.google.com can be reached.



How stable is the software?
---------------------------
It is totally beta and uses a mix of user unfriendly technology.
You have been warned.


Prerequisites
-------------
- Java 11
- Mac OS X


Building and running
--------------------

    mvn package
    java -jar ./target/ismyinternetbroken-1.0-SNAPSHOT-jar-with-dependencies.jar