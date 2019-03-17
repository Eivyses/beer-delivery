FROM mysql

# Environment variables
#If you change PASSWORD, change it in sql/sha2_password.sql file also
ENV MYSQL_ROOT_PASSWORD admin
ENV MYSQL_DATABASE beer_delivery

# Add the content of the sql-scripts/ directory to your image
# All scripts in docker-entrypoint-initdb.d/ are automatically
# executed during container startup
COPY ./sql/ /docker-entrypoint-initdb.d/