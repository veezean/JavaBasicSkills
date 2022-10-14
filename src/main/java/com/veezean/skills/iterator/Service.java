package com.veezean.skills.iterator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/9/20
 */
public class Service {

    public List<Requirement> getAllClosedRequirements(Project project) {
        return project.getRequirements().stream()
                .filter(requirement -> requirement.getStatus() == 1)
                .sorted((o1, o2) -> (int) (o2.getCreateTime().getTime() - o1.getCreateTime().getTime()))
                .collect(Collectors.toList());
    }

    public List<Requirement> getAllClosedRequirements2(Project project) {
        List<Requirement> requirements = project.getRequirements();
        List<Requirement> resultList = new ArrayList<>();
        for (Requirement requirement : requirements) {
            if (requirement.getStatus() == 1) {
                resultList.add(requirement);
            }
        }
        resultList.sort((o1, o2) -> (int) (o2.getCreateTime().getTime() - o1.getCreateTime().getTime()));
        return resultList;
    }

    public static void main(String[] args) {

        Project project = new Project();

        Requirement req1 = new Requirement();
        req1.setStatus(1);
        req1.setCreateTime(new Date());

        try {
            Thread.sleep(1000);

        } catch (Exception e) {

        }

        Requirement req2 = new Requirement();
        req2.setStatus(1);
        req2.setCreateTime(new Date());

        List<Requirement> reqs = new ArrayList<>();
        reqs.add(req1);
        reqs.add(req2);

        project.setRequirements(reqs);

        Service service = new Service();

        List<Requirement> allClosedRequirements = service.getAllClosedRequirements(project);
        for (Requirement req : allClosedRequirements) {
            System.out.println(req.getCreateTime());
        }
    }
}
