package com.specialiststeak.learn_api.objects;

import java.util.List;

public record ExampleObject (int id, String name, String description, List<String> tags){
}
