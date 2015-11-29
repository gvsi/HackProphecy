import os
from flask import Flask
from flask import render_template
from flask import jsonify
app = Flask(__name__, static_url_path='/static')

@app.route('/')
def home():
    return render_template('index.html')
@app.route('/predict/<name>')
def predict(name):
    return jsonify(win=True,
                  message='test')
@app.errorhandler(404)
def page_not_found(error):
    return render_template('404.html'), 404

if __name__ == '__main__':
    app.run()
    
