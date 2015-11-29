import os
from flask import json
from flask import Flask
from flask import render_template
from flask import jsonify
app = Flask(__name__, static_url_path='/static')

with open('data_ids.json') as data_file:
    data = json.load(data_file)
    
def find_in_data(name):
    for x in data:
        if x['id'] == name:
            return x

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/predict/')
def predict_empty():
    return jsonify(win=False,
                  message="Error project not found")
@app.route('/predict/<name>')
def predict(name):
    message=find_in_data(name)
    return jsonify(win=False,
                  message=json.dumps(message, indent=4, separators=(',', ': ')))

@app.errorhandler(404)
def page_not_found(error):
    return render_template('404.html'), 404

if __name__ == '__main__':
    app.run()
    
