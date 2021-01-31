## Name

Movie Database REST Web Services

## Description

A REST Web Services CRUD system involving movies using Spring Framework and PostgreSQL as database. I have tried to use best practises through the whole application's development, so it is quite easy
to keep updating and adding more movie related modules in the future.

Some of the worth pointing out development sections implemented are:\
\
&emsp;&#8226;&emsp;Two different layer DTOs, one for sharing data to the presentation layer and the other one for validating new movie entries or updates,
not only making safer the transactions with user, but also making the classes of models more readable and cleaner\
\
&emsp;&#8226;&emsp;Flyway database migration tool\
\
&emsp;&#8226;&emsp;Exception Handling\
\
&emsp;&#8226;&emsp;Unit Tests\
\
&emsp;&#8226;&emsp;Integration Tests using h2 in-memory database\

## Features

Project's features are:\
\
&emsp;&#8226;&emsp;Create <Entity>\
\
&emsp;&#8226;&emsp;Read <Entity>\
\
&emsp;&#8226;&emsp;Update <Entity>\
\
&emsp;&#8226;&emsp;Delete <Entity>

