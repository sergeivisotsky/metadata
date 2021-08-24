@echo off

call docker stop db
call docker rm -f db
call docker-compose -p db up -d --build