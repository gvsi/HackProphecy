import json
from pprint import pprint

with open('data.json') as data_file:
   data = json.load(data_file)

#pprint([x for x in data if x['winner'] == True])
print(len(data))
