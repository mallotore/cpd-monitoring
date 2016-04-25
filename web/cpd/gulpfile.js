var gulp = require('gulp');
var rename = require('gulp-rename');
var gutil = require('gulp-util');
var browserify = require('browserify');
var watchify = require('watchify');
var globby = require('globby');
var source = require('vinyl-source-stream');
var buffer = require('vinyl-buffer');
var uglify = require('gulp-uglify');
var sourcemaps = require('gulp-sourcemaps');
var _ = require('lodash');
var notify = require('gulp-notify');
var babelify = require('babelify');
var presetsReact = require('babel-preset-react');
var path = require("path");
var del = require("del");
var destFolder = 'grails-app/assets/javascripts/dist';
var sourceFilesPattern = './grails-app/assets/javascripts/**/*.jsx';

gulp.task('devel-bundle', function(){

    var sourceFiles = globby.sync(sourceFilesPattern);

    _(sourceFiles).forEach(function (filePath) {
        var watchified = watchify(browserify({
                            entries: globby.sync(filePath),
                            debug: true
                         }).transform(babelify, { presets: [presetsReact] }))
                        .on('update', updateWatchifiedFilesEventhandler)
                        .on('time', timeWatchifiedFilesEventHandler)
                        .on('log', logWatchifiedEventHandler);
        bundle(watchified);
        return;

        function updateWatchifiedFilesEventhandler(ids) {
            var title = 'Building affected files';
            _(ids).forEach(function (id) {
                notify({
                    title: title,
                    message: id,
                    templateOptions: {
                        date: new Date()
                    },
                    onLast: false,
                    wait: true
                }).write(id);
                gutil.log(gutil.colors.yellow(title + ' -> '), gutil.colors.green(id));
            });
            bundle(watchified, filePath);
        }

        function timeWatchifiedFilesEventHandler(time) {
            gutil.log(gutil.colors.green('Browserify'),
                        gutil.colors.yellow(filePath),
                        gutil.colors.green(' in ' + time + ' ms'));
        }

        function logWatchifiedEventHandler(message) {
            gutil.log(gutil.colors.green(message));
        }

        function bundle(browserified) {
            return browserified
                .bundle()
                .on('error', notifyError)
                .pipe(source(filePath))
                .pipe(rename({ dirname: '' }))
                .pipe(buffer())
                .pipe(sourcemaps.init({ loadMaps: true }))
                .pipe(uglify())
                .on('error', notifyError)
                .pipe(sourcemaps.write())
                .pipe(rename(function (path) {
                    if (path.extname != '.map') {
                        path.extname = ".min.js";
                    }
                }))
                .pipe(gulp.dest(destFolder))
                .pipe(notify({
                    title: "JS BUNDLE",
                    message: "Task complete <%= file.relative %> @ <%= options.date %>",
                    templateOptions: {
                        date: new Date()
                    },
                    icon: path.join(__dirname, 'ico', 'success.png'),
                    onLast: false,
                    wait: true
                }));
        }

        function notifyError(error) {
            notify({
                title: "JS BUNDLE",
                subtitle: "Task with error!!!",
                message: "Error: <%= error.message %>",
                templateOptions: {
                    date: new Date()
                },
                //icon: path.join(__dirname, 'ico', 'error.png'),
                onLast: false,
                wait: false
            }).write(error);
            this.emit('end');
        }
    });
});

gulp.task('clean', function() {
    gutil.log(gutil.colors.green('Cleaning folder: ' + destFolder));
    del(destFolder);
});

gulp.task('devel', ['clean', 'devel-bundle']);