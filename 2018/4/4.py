def datetimeComesBefore(time1, time2): # Returns true if date 1 comes before date 2
    month1 = int(time1[5:7])
    month2 = int(time2[5:7])

    if month1 < month2:
        return True
    elif month1 == month2:
        day1 = int(time1[8:10])
        day2 = int(time2[8:10])

        if day1 < day2:
            return True
        elif day1 == day2:
            # Compare times
            hour1 = int(time1[11:13])
            hour2 = int(time2[11:13])

            if (hour1 < hour2):
                return True
            elif hour1 == hour2:
                minute1 = int(time1[14:16])
                minute2 = int(time2[14:16])
                if minute1 < minute2:
                    return True
                else:
                    return False
            else:
                return False
        else:
            return False
    else:
        return False

with open("puzzleInput.txt") as f:
    guardTimes = f.readlines()

guardDict = {}
'''
for day in guardTimes:
    
    date = day[1:11]

    if (date not in guardDict.keys()):
        guardDict[date] = []

    guardDict[date].append(day.rstrip())

print(guardDict)
'''

# We need to sort the input
# Insertion sort

guardTimesUnsorted = guardTimes
guardTimes = []

for event in guardTimesUnsorted:

    if len(guardTimes) == 0:
        guardTimes.append(event)
    else:
        # Look for the event that it comes before
        for i, event2 in enumerate(guardTimes):
            dateTime1 = event[1:17]
            dateTime2 = event2[1:17]
            if datetimeComesBefore(dateTime1, dateTime2):
                guardTimes.insert(i, event)
                break

            if i == len(guardTimes)-1:
                guardTimes.append(event)
                break

    
print(guardTimes)



# Lets go through the dict and add up the totals of the times that the guards are asleep

guardSleepTotals = {}
guardSleepMinutes = {}

for event in guardTimes:
    if "shift" in event:
        guardRaw = event.split("Guard")[1]
        guardRaw = guardRaw.replace("#", "")
        guardRaw = guardRaw.split()[0]
        guardID = guardRaw

    if "falls" in event:
        sleepTime = int(event[15:17])

    if "wakes" in event:
        wakeTime = int(event[15:17])

        if guardID not in guardSleepTotals.keys():
            guardSleepTotals[guardID] = 0

        guardSleepTotals[guardID] += (wakeTime - sleepTime)

        if guardID not in guardSleepMinutes.keys():
            guardSleepMinutes[guardID] = {}

        for i in range(sleepTime, wakeTime):
            if str(i) not in guardSleepMinutes[guardID].keys():
                guardSleepMinutes[guardID][str(i)] = 0
            
            guardSleepMinutes[guardID][str(i)] += 1

print(guardSleepTotals)
print(guardSleepMinutes)

mostSleepID = ""
mostSleep = 0
for guard in guardSleepTotals.keys():
    if guardSleepTotals[guard] > mostSleep:
        mostSleepID = guard
        mostSleep = guardSleepTotals[guard]

print(mostSleepID)

# We have the guard that slept the most now so we just need to figure out
# which minute they were asleep in the most 

mostSleepMinute = ""
mostTimesAsleepInMinute = 0

for minute in guardSleepMinutes[mostSleepID].keys():
    if guardSleepMinutes[mostSleepID][minute] > mostTimesAsleepInMinute:
        mostTimesAsleepInMinute = guardSleepMinutes[mostSleepID][minute]
        mostSleepMinute = minute

print (mostSleepMinute)

print (int(mostSleepMinute) * int(mostSleepID))


# Part 2

mostSleepGuardID = ""
mostSleepTimesInMinute = 0
mostSleepMinute = ""
for guard in guardSleepMinutes.keys():
    for minute in guardSleepMinutes[guard].keys():
        if guardSleepMinutes[guard][minute] > mostSleepTimesInMinute:
            mostSleepGuardID = guard
            mostSleepTimesInMinute = guardSleepMinutes[guard][minute]
            mostSleepMinute = minute

print(int(mostSleepGuardID) * int(mostSleepMinute))