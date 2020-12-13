import math

with open("input.txt") as f:

    lines = [line.strip() for line in f.readlines()]

    earliest_timestamp = int(lines[0])
    buses = [int(bus) for bus in lines[1].split(",") if bus != "x"]



# Part 1
current_closest_bus = -1
current_closest_bus_time = -1

for bus in buses:

    current_time = 0
    while current_time < earliest_timestamp:
        current_time += bus

    
    if current_time < current_closest_bus_time or current_closest_bus_time == -1:
        current_closest_bus_time = current_time
        current_closest_bus = bus

    print("time: {0}, bus: {1}".format(current_closest_bus_time, current_closest_bus))


print(current_closest_bus * (current_closest_bus_time - earliest_timestamp))

# Part 2

def lcm(a,b):
    return abs(a*b) // math.gcd(a, b)

# Slightly change the input
with open("input.txt") as f:

    bus_line = [line.strip() for line in f.readlines()][1].split(",")

    buses = []
    for bus in bus_line:
        if bus != "x":
            buses.append(int(bus))
        else:
            buses.append(bus)

current_time = buses[0]
last_time = 0 # Just for printing
    
time_step = buses[0]
while True:

    if current_time > last_time * 2:
        print("trying time: {}".format(current_time))
        last_time = current_time
    valid = True
    
    for bus_offset in range(1, len(buses)):

        bus = buses[bus_offset]
        if bus == "x":
            continue
        if (current_time + bus_offset) % bus != 0:
            #print("trying bus: {}, with offset: {}".format(bus, bus_offset))

            if valid:
                # update the time step
                # Magic maths
                # We know that this current time works for all the buses before this current bus that it failed on
                # So every new solution (which work for these buses) must be of the form this time plus the lowest common multiple of the times of these buses
                # As this is the smallest duration in which all the buses complete an integer number of cycles and so prevserves this solution
                # lcm of previous buses

                time_step = 1
                for bus_index in range(0, bus_offset):
                    if buses[bus_index] == "x":
                        continue
                    time_step = lcm(time_step, buses[bus_index])

                print("new time_step: {}".format(time_step))
            valid = False

            break
    
    if valid:

        print(current_time)
        break
    
    current_time += time_step