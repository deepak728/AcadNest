#!/bin/bash

# Array to track all process PIDs
PIDS=()

# Function to kill all processes when the script exits
cleanup() {
  echo "Stopping all services..."
  for pid in "${PIDS[@]}"; do
    kill "$pid" 2>/dev/null
  done
  wait
  exit
}

# Set trap to call cleanup when script exits
trap cleanup SIGINT SIGTERM EXIT

echo "Starting Eureka Server..."
cd registry-service && mvn spring-boot:run &
PIDS+=($!)  # Store PID
sleep 10  # Wait for Eureka to start

echo "Starting API Gateway..."
cd api-gateway && mvn spring-boot:run &
PIDS+=($!)  # Store PID
sleep 5

echo "Starting Auth Service..."
cd auth-service && mvn spring-boot:run &
PIDS+=($!)  # Store PID
sleep 5

echo "Starting People Service..."
cd people-service && mvn spring-boot:run &
PIDS+=($!)  # Store PID

# Wait for all background processes
wait
