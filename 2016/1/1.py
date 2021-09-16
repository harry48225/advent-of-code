import re, math

with open('input.txt') as f:
    puzzleinput = f.read()

position=[0, 0]
face = 0 # along y axis, incrementing goes cw
dpos = {0: [0, 1], 1: [1, 0], 2: [0, -1], 3: [-1, 0]}

position_stack = [position[:]]; ans2_found = False

for rawinst in puzzleinput.strip().split(', '):
    rotation = rawinst[0]
    distance = int(rawinst[1:])
    if rotation == 'R':
        face = (face + 1)%4
    if rotation == 'L':
        face = (face - 1)%4
    
    for _ in range(distance):
        position[0] += dpos[face][0]
        position[1] += dpos[face][1]
        
        if position in position_stack and not ans2_found:
            print("answer 2: manhattan dist to first dupe = {}".format(sum(map(abs, position))))
            ans2_found = True
        
        position_stack.append(position[:])

print("answer 1: final manhattan dist = {}".format(sum(map(abs, position))))