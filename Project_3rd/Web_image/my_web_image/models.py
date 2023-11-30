# models.py

from django.db import models

class Image(models.Model):
    image = models.ImageField(upload_to='images/')
    upload_date = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.image.name
