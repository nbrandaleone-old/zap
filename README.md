# zap

A Clojure library designed to ... well, that part is up to you.

## Usage

FIXME

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
# zap

Create the initial database, using the schema.
$ sqlite3 -init resources/data/schema.sql zap.db .quit

Start the server
$ lein ring server
