# Tick42-ExtensionRepository

[![codecov](https://codecov.io/gh/Smytt/Tick42-ExtensionRepository/branch/master/graph/badge.svg)](https://codecov.io/gh/Smytt/Tick42-ExtensionRepository)
[![Build Status](https://travis-ci.org/Smytt/Tick42-ExtensionRepository.svg?branch=master)](https://travis-ci.org/Smytt/Tick42-ExtensionRepository)

Tick42-ExtensionRepository a.k.a Ext42 is a web platform which provides extension developers with the ability to share their work along with a github repository link. Every user can receive ratings for his extensions and also keep count on how many times they were downloaded. The app provides 3 different levels of accessability:

## Guest

A Guest user can browse through the extensions, search and download them.

## Registered user

A registered user can do everything the guest can, plus the following:
* upload, edit and delete their own extensions - including image and file upload and linking to a github repository
* rate others' extensions and receive ratings for own extensions
* update own password

## Administrator

An administrator can do everything a registered user can, plus the following:
* enable / diasble user profiles along with all their extensions
* enable / disable extensions
* feature / unefature extensions
* force github data refresh - fetch latest data for last commit date, pull requests and open issues
* set refresh time for fetching all extensins' github data
* register additional admin accounts

### How is it made?

The back end is a RestAPI created in Java and SpringMVC. Hibernate is used for ORM and the DB is MariaDB.
The front end is html / css + mustache.js for rendering the html templates.

### How do I run it?

You need MariaDB and JDK 1.8.

Execute the sql:
`$ mysql -u root -p < database.sql`

Run the app:
`$ ./gradlew bootRun`

The ap runs on the :8080 port by default, so make your requests to http://localhost:8080/

There is one admin user preset in the database:
`username: SystemAdmin`, `pssword: SystemDefaultPassword1`

### Where is the documentation?
Find all the request routs here: .....
