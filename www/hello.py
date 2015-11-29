from flask import Flask
from flask import render_template
app = Flask(__name__, static_url_path='/static')

@app.route('/')
def home():
    return render_template('index.html')
@app.route('/<name>')
def predict():
    return render_template('predict.html')
@app.errorhandler(404)
def page_not_found(error):
    return render_template('page_not_found.html'), 404

if __name__ == '__main__':
    app.run()