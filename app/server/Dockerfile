FROM node:lts-alpine
WORKDIR /api
COPY ./package*.json ./
RUN npm i --production
COPY ./ ./
CMD ["node","server.js"]