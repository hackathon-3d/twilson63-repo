module.exports = function(grunt) {
  grunt.initConfig({
    jshint: {
      files: ['app/app.js', 
      'app/directives/**/*.js',
      'app/controllers/**/*.js',
      'app/server/**/*.js']
    },
    concat: {
      options: {
        separator: ";"
      },
      dist: {
        src: [
          'app/components/angular/angular.js',
          'app/components/angular-fire/angularFire.js',
          'app/app.js', 
          'app/directives/**/*.js',
          'app/controllers/**/*.js',
          'app/server/**/*.js'],
        dest: 'public/js/app.js'
      }
    }, 
    connect: {
      server: {
        options: {
          port: 3000,
          base: 'public'
        }
      }
    },
    
    watch: {
      files: ['app/**/*.js', 'test/**/*.js'],
      tasks: ['jshint', 'concat']
    },
    karma: {
      options: {
        configFile: 'karma.conf.js',
        browsers: ['Chrome']
      },
      continuous: {
        singleRun: true,
        browsers: ['PhantomJS']
      },
      dev: {
        reporters: 'dots',
        background: true
      }
    },
    less: {
      bootstrap: {
        options: {
          paths: ["public/css"]
        },
        files: {
          "public/css/bootstrap.css": "app/components/bootstrap/less/bootstrap.less"
        }
      }
    }
  });

  grunt.loadNpmTasks('grunt-karma');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-contrib-less');

  grunt.registerTask('default', ['jshint']);
  grunt.registerTask('server', ['connect:server', 'watch']);
}
