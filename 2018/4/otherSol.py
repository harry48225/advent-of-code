from datetime import datetime

INFIL = 'puzzleInput.txt'
def sleepy_bois(tstamp_act):
    guards = {}
    er = 0
    for entry in tstamp_act:
        if 'begins shift' in entry[1]:
            guard_id = entry[1][entry[1].index('#'):entry[1].index('b') - 1]
            if guard_id not in guards:
                guards[guard_id] = 0
        elif 'falls asleep' in entry[1]:
            cont = -1
            while tstamp_act[tstamp_act.index(entry) + cont][1][0] != 'G':
                cont -= 1
            guard_id_str = tstamp_act[tstamp_act.index(entry) + cont][1]
            guard_id = guard_id_str[guard_id_str.index('#'):guard_id_str.index('b') - 1]
            wake_up = tstamp_act[tstamp_act.index(entry) + 1][0].minute
            sleep = tstamp_act[tstamp_act.index(entry)][0].minute
            time_sleeping = wake_up - sleep
            guards[guard_id] = guards[guard_id] + time_sleeping
    return(guards)

def sort_sched(infil):
    data = open(infil, 'r+')
    lines = data.readlines()
    data.close()
    tstamp_act = []
    for line in lines:
        timestamp, action = line[1:line.index(']')], line[line.index(']') + 2::].strip()
        tstamp_act.append((datetime.strptime(timestamp, '%Y-%m-%d %H:%M'), action))
    tstamp_act.sort()
    return(tstamp_act)

def most_sleepy_boi(guards):
    max_sleep = 0
    sleepy_boi_id = ''
    for k,v in guards.items():
        if (v > max_sleep):
            sleepy_boi_id = k
            max_sleep = v
    return(sleepy_boi_id)

def most_asleep_min(sched, sleeper_id):#, sleepy_guard):
    times = []
    for entry in sched:
        if entry[1][0] == 'G':
            if entry[1][entry[1].index('#'):entry[1].index('b') - 1] == sleeper_id:
                cont = 1
                try:
                    while(sched[sched.index(entry) + cont][1][0] != 'G'):
                        if (sched[sched.index(entry) + cont][1][0] == 'f'):
                            wake_up = sched[sched.index(entry) + cont + 1][0].minute
                            sleep = sched[sched.index(entry) + cont][0].minute
                            for i in range(sleep, wake_up):
                                times.append(i)
                            cont += 2
                except IndexError:
                    pass
    # return times
    return(max(set(times), key = times.count))
sleeper_id = int((most_sleepy_boi(sleepy_bois((sort_sched(INFIL)))))[1::])
sleeper_min = (most_asleep_min(sort_sched(INFIL), most_sleepy_boi(sleepy_bois((sort_sched(INFIL))))))
print(sleeper_id * sleeper_min)