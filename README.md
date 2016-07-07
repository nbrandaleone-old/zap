# zap

A Clojure driven web site. The site emulates a simple
ticket based bug tracking system.

The code was copied/modified from the Clojure chapter (Chapter 4 - Ring)
of "Seven web frameworks in seven weeks", published
by The Pragmatic Programmers

## Usage

Nick Brandaleone
December 2015

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

# zap

Create the initial database, using the schema.
$ sqlite3 -init resources/data/schema.sql zap.db .quit

Start the server
$ lein ring server

I created a docker container for running this web site.
I collected the code into a jar/uberjar. I should consider a WAR file?!
DOCUMENT the Docker process.
