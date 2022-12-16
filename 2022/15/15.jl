using LinearAlgebra

function parseCoords(coords)
  x, y = map(s -> split(s, "=")[2], split(coords, ", "))
  return parse.(Int, [x,y])
end

struct Sensor
  coords::Vector{Int}
  radius::Int
end

beacons = Set()
sensors = Set()

inp = readlines("input.txt")

for line in inp
  rawSensor, rawBeacon = split(line, ": ")
  sensorCoords = parseCoords(rawSensor[length("Sensor at ")+1:end])
  beaconCoords = parseCoords(rawBeacon[length("closest beacon is at ")+1:end])

  push!(beacons, beaconCoords)
  radius = norm(beaconCoords - sensorCoords, 1)
  push!(sensors, Sensor(sensorCoords, radius))
end

function findNoBeacons(line)
  noBeacons = range(1,0)
  
  for sensor in sensors
    # println(sensor)
    if line in (sensor.coords[2] - sensor.radius):(sensor.coords[2] + sensor.radius)    
      extent = (sensor.radius - abs(sensor.coords[2] - line)) 

      r = range((sensor.coords[1] - extent),(sensor.coords[1] + extent))
      noBeacons = union(noBeacons, r)
    end
  end

  return noBeacons
end

row = 2000000
no = findNoBeacons(row)
total = length(no) - length(filter(b-> b[2] == row && b[1] in no, beacons))
println(total)

#5832528 correct

bound = 4000000
# x_range = 0:bound
# y_range = 0:bound

# must be 1 away from the circumfrence of at least one sensor
for sensor in sensors
  # println(sensor)
  radius = sensor.radius + 1
  for dx in -radius:radius
    dy = radius - dx
    for p in [sensor.coords + [dx, dy], sensor.coords + [dx, -dy]]
      valid = true
      for ss in sensors
        if norm(p - ss.coords, 1) <= ss.radius || p[1] > bound || p[2] > bound || p[1] < 0 || p[2] < 0
          valid = false
          break
        end
      end
      if valid
        println("$(p[1]*4000000 + p[2])")
        exit()
      end
    end
  end  
end