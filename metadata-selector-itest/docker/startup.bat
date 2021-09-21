@echo off

call docker stop itest-db
call docker rm -f itest-db
call docker-compose -p itest-db up -d --build