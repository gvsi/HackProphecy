import json
from pprint import pprint

with open('data.json') as data_file:
   data = json.load(data_file)

#pprint([x for x in data if x['winner'] == True])
#print(len(data))

# Win vector
win_vector = ["1" if x['winner'] else "0" for x in data]
print(win_vector)
