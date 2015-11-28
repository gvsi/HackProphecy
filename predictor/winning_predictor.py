import json
from pprint import pprint
from tag import count_words
from sets import Set

with open('data.json') as data_file:
   data = json.load(data_file)

# pprint([x for x in data if x['winner'] == True])
# print(len(data))

# Win vector
win_vector = [1 if x['winner'] else 0 for x in data]
print(win_vector)

count_vectors = [count_words(x['description']) for x in data]

for x in count_vectors:
    sumo = sum([y[1] for y in x])
    for i in range(0,len(x)):
        if sumo != 0:
            x[i] = (x[i][0], float(x[i][1]) / sumo)

print(count_vectors)

words = Set()

for x in count_vector:
    for word_tuple in x:
        words.add(word_tuple[0])

print(words)

matrix = []
for word in words:
    vector = []
    for count in count_vectors:
        for tuple_count in count:
            if tuple_count[0] == word:
                vector.add(tuple_count[1])
                break
    matrix.add(vector)


    # for z in x:
    #     if sumo != 0:
    #         z = (z[0], z[1] / sumo)
