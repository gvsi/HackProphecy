import json
from pprint import pprint
from tag import count_words
from sets import Set
import sys
import random
from sklearn import svm
import numpy as np

from sklearn.externals import joblib

with open('projects_with_users.json') as data_file:
   data = json.load(data_file)

# pprint([x for x in data if x['winner'] == True])
# print(len(data))
#random.shuffle(data)
# Win vector
#win_vector = [1 if x['winner'] else 0 for x in data]
#print(win_vector)

count_vectors = [count_words(x['description']) for x in data]

words = [ ]
for x in count_vectors:
	for pair in x:
		if words.count(pair[0]) == 0:
			words.append(pair[0])

maxOcc = [ ]
minOcc = [ ]
for j in range(0, len(words) + 3):
	maxOcc.append(0)
	minOcc.append(0)

matrix = [ ]
for i in range(0, len(count_vectors)):
	row = [ ]
	for j in range(0, len(words)):
		cnt = 0
		for item in count_vectors[i]:
			if words[j] == item[0]:
				cnt = item[1]
		row.append(cnt)
		if cnt > maxOcc[j]:
			maxOcc[j] = cnt
		if cnt < minOcc[j]:
			minOcc[j] = cnt
	matrix.append(row)

row = 0
for element in data:
	nr_wins = 0
	nr_projects = 0
	nr_hacks = 0
	for author in element["authors"]:
		nr_wins = nr_wins + author["wins"]
		nr_projects = nr_projects + author["projects"]
		nr_hacks = nr_hacks + author["hackathons"]

	if nr_projects == 0:
            matrix[row].append(0)
        else:
            matrix[row].append(float(nr_wins) / nr_projects)
	matrix[row].append(nr_wins)
	matrix[row].append(nr_hacks)

	row = row + 1

for i in range(0, len(matrix)):
    for j in range(0, len(matrix[i])):
        if matrix[i][j] > maxOcc[j]:
            maxOcc[j] = matrix[i][j]

for i in range(0, len(matrix)):
    for j in range(0, len(matrix[i])):
        matrix[i][j] = float(matrix[i][j]) / maxOcc[j]


np.savetxt('matrix.txt', win_vector, fmt="%.6f")

data = np.loadtxt('matrix.txt').tolist()

# print(len(win_vector))
#
# for i in range(0, len(win_vector)):
#     if (win_vector[i] != data[i]):
#         print(i)

# print("prediction:", clf.predict(matrix[-10])[0])
# print("actual:", win_vector[-10])


# print matrix[1]
#
