# ripplegl
Ripple Tank, webgl version

## Introduction

RippleGL is a ripple tank simulation that runs in the browser. It was originally written by me, Paul Falstad, as a Java Applet.  Then Iain Sharp adapted one of my other applets, a circuit simulator, to run in the browser using GWT.  So I used some of his code to build a similar port of the java ripple tank to GWT.  In the process, I also converted the simulation code to WebGL, for much better performance.

For a hosted version of the application see http://www.falstad.com/ripple/

## Building the web application

The tools you will need to build the project are:

* Maven

Run the following to build the application:
```
mvn clean package
```

You can then open the html page at `./ripple-server/target/ripple-server-1.0-SNAPSHOT/Ripple.html`.

## Choose other startup example

If you want to display other example instead of the default `Single-Source`, you can add a query parameter
to the url:

```
./ripple-server/target/ripple-server-1.0-SNAPSHOT/Ripple.html?startExample=<name>
```

For example, you can replace `<name>` by `Two-Source` to display the `Two-Source` example.

## License

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
