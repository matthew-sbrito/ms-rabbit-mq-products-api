FROM maven:3.8.7-amazoncorretto-17

WORKDIR /app

COPY . /app

EXPOSE ${PORT}

CMD ["./startup"]